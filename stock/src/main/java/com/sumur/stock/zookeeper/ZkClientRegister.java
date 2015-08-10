package com.sumur.stock.zookeeper;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkTimeoutException;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkClientRegister {

	private static final Logger log = LoggerFactory.getLogger(ZkClientRegister.class);

	private static final int DEFAULT_CONNECTION_TIMEOUT = 20 * 1000; 
	
	private static final int DEFAULT_SESSION_TIMEOUT = 20 * 1000; 
	
	public static void init() {
        Properties props = new Properties();
        InputStream propsStream = ZkClientRegister.class.getResourceAsStream("/zookeeper.properties");
        try {
            props.load(propsStream);
        } catch (IOException e) {
            log.error("load prop error", e);
        }
        
        String connect = props.getProperty("zookeeper.connect");
        String timoutStr = props.getProperty("zookeeper.timeout");
        String path = props.getProperty("zookeeper.path");
    	int sessionTimeout = DEFAULT_SESSION_TIMEOUT;
    	if (StringUtils.isNumeric(timoutStr)) {
    		sessionTimeout = Integer.parseInt(timoutStr);
		}

    	try {
            //sessionTimeOut采用系统默认不做设置 30s
        	final ZkClient zkClient  = new ZkClient(connect, sessionTimeout, DEFAULT_CONNECTION_TIMEOUT);
        	log.info("Create zookeeper's connection successfully!");
        	//创建持久化根目录 的tigases根目录
        	if (!zkClient.exists(path)) {
        		zkClient.createPersistent(path);
			}
        	
        	final String hostName = InetAddress.getLocalHost().getHostName();
        	final String hostPath = path + "/" + hostName;
    		
        	zkClient.subscribeStateChanges(new IZkStateListener() {
    			@Override
    			public void handleStateChanged(KeeperState state) throws Exception {
    				if (state == KeeperState.Disconnected) {
    					log.warn("Zookeeper's connection is disconnected");
    				} else if (state == KeeperState.SyncConnected) {
    					log.warn("Zookeeper's connection is syncConnected");
    				} else if (state == KeeperState.Expired) {
    					log.warn("Zookeeper's connection is expired");
    				}
    			}

    			@Override
    			public void handleNewSession() throws Exception {
    				//该处需要重新维持临时节点采用重新创建方式
    				createOrUpdateEphemeral(zkClient, hostPath, hostName);
    				log.warn("Zookeeper's connection is reconnected");				
    			}
    		});
        	
        	//首次创建临时节点
        	createOrUpdateEphemeral(zkClient, hostPath, hostName);
        	
        } catch (ZkTimeoutException e) {
        	log.error("Connect zookeeper error", e);
            //如果出现异常需要做的事情
        } catch (UnknownHostException e) {
        	log.error("get localhost error", e);
		}
	}
	
	private static void createOrUpdateEphemeral(ZkClient zkClient, String path, Object data) {
		if (!zkClient.exists(path)) {
			zkClient.createEphemeral(path, data);
		} 
		else {
			zkClient.delete(path);
			zkClient.createEphemeral(path, data);
	   }
	}

}
