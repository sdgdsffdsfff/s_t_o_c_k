package other.sumur.java.test.zookeeper;


import java.util.Date;

import net.sf.json.JSONObject;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;

public class ZKtest1 {
	@Test
	public void test1() throws InterruptedException{
		ZkClient zkClient = new ZkClient("localhost:2181");
		zkClient.subscribeDataChanges("/root", new IZkDataListener() {
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("delete--"+dataPath);
			}
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("change--"+dataPath+"---"+data);
			}
		});
		Thread.sleep(1000 * 1000);
	}
	
	@Test
	public void test2(){
		String subjectpath = "/root";
		ZkClient zkClient = new ZkClient("localhost:2181");
		if(!zkClient.exists(subjectpath)){
			zkClient.createPersistent(subjectpath);
			return;
		}
		zkClient.writeData(subjectpath, new Date());
	}
	
	@Test
	public void test3(){
		try{
			System.out.println("try");
			return;
		}finally{
			System.out.println("finally");
		}
	}
	
	@Test
	public void test4(){
		JSONObject json = JSONObject.fromObject(new Date());
		System.out.println(json);
		String x = json.toString();
		JSONObject j = JSONObject.fromObject(x);
		System.out.println(j);
	}
}