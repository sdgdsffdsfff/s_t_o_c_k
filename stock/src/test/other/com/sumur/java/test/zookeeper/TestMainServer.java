package com.sumur.java.test.zookeeper;

import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.junit.Assert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TestMainServer
 * <p/>
 * Author By: junshan
 * Created Date: 2010-9-3 16:17:21
 */
public class TestMainServer extends ZooKeeperServerMain {
    public static final int CLIENT_PORT = 2181;
    public static class MainThread extends Thread {
        final File confFile;
        final TestMainServer main;
        
        private File createTmpDir() throws IOException {
            File tmpFile = File.createTempFile("test", ".junit", new File(System.getProperty("build.test.dir", "build")));
            File tmpDir = new File(tmpFile + ".dir");
            Assert.assertFalse(tmpDir.exists());
            Assert.assertTrue(tmpDir.mkdirs());

            return tmpDir;
        }

        public MainThread(int clientPort) throws IOException {
            super("Standalone server with clientPort:" + clientPort);
            File tmpDir = createTmpDir();
            confFile = new File(tmpDir, "zoo.cfg");

            FileWriter fwriter = new FileWriter(confFile);
            fwriter.write("tickTime=2000\n");
            fwriter.write("initLimit=10\n");
            fwriter.write("syncLimit=5\n");

            File dataDir = new File(tmpDir, "data");
            if (!dataDir.mkdir()) {
                throw new IOException("unable to mkdir " + dataDir);
            }
            String df = org.apache.commons.lang.StringUtils.replace(dataDir.toString(),"\\","/");
            fwriter.write("dataDir=" + df + "\n");

            fwriter.write("clientPort=" + clientPort + "\n");
            fwriter.flush();
            fwriter.close();

            main = new TestMainServer();
        }

        public void run() {
            String args[] = new String[1];
            args[0] = confFile.toString();
            try {
                main.initializeAndRun(args);
            } catch (Exception e) {
                
            }
        }
    }
    public static void start(){

        MainThread main = null;
        try {
            main = new MainThread(CLIENT_PORT);
        } catch (IOException e) {
            return ;
        }
        main.start();
    }

    public static void main(String[] args) {
        TestMainServer.start();
    }
}
