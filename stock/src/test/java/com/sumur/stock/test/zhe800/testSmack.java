package com.sumur.stock.test.zhe800;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.PacketInterceptor;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.iqversion.packet.Version;
import org.springframework.data.redis.core.RedisTemplate;

public class testSmack extends Thread {

	String host = "192.168.100.223";
	String serviceName = "im.zhe800.com";
	String sender;
	String receiver;
	String password;
	String suffix;
	boolean isAlive = true;

	public static RedisTemplate<String, String> redisTemplate;

	public testSmack(String sender, String receiver, String password) {
		this.sender = sender;
		this.receiver = receiver;
		this.password = password;
	}

	public void userLogin(XMPPConnection tcpConnect, String userName, String password, String suffix) throws Exception {
		tcpConnect.login(sender, password, suffix);
	}

	public void createUser(XMPPConnection tcpConnect, String userName, String password) {
		try {
			AccountManager accoutManager = AccountManager.getInstance(tcpConnect);
			accoutManager.createAccount(userName, password);
		} catch (NoResponseException | XMPPErrorException | NotConnectedException e) {
			e.printStackTrace();
		}
	}

	public void changUserPassword(XMPPConnection tcpConnect, String password) {
		try {
			AccountManager accoutManager = AccountManager.getInstance(tcpConnect);
			accoutManager.changePassword(password);
			accoutManager = AccountManager.getInstance(tcpConnect);
		} catch (NoResponseException | XMPPErrorException | NotConnectedException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(XMPPConnection tcpConnect) {
		try {
			AccountManager accoutManager = AccountManager.getInstance(tcpConnect);
			accoutManager.deleteAccount();
		} catch (NoResponseException | XMPPErrorException | NotConnectedException e) {
			e.printStackTrace();
		}
	}

	public void getAllGroupAndUsers(Roster roster) {
		Collection<RosterGroup> rgs = roster.getGroups();
		for (RosterGroup rg : rgs) {
			System.out.println("getAllGroupAndUsers group name = " + rg.getName());
			Collection<RosterEntry> res = rg.getEntries();
			for (RosterEntry re : res)
				System.out.println("getAllGroupAndUsers entry name = " + re.getUser());
		}
	}

	public void getAllUserAndGroup(Roster roster) {
		for (RosterEntry re : roster.getEntries()) {
			Collection<RosterGroup> rgs = re.getGroups();
			StringBuffer sb = new StringBuffer();
			for (RosterGroup rg : rgs) {
				sb.append(rg.getName() + ",");
			}
			System.out
					.println("getAllUserAndGroup entry name = " + re.getUser() + ", groups = " + sb + ", status = " + re
							.getType());
		}
	}

	public void createUserNoGroup(Roster roster, String userName, String name) {
		try {
			roster.createEntry(userName, name, null);
		} catch (NotLoggedInException | NoResponseException | XMPPErrorException | NotConnectedException e) {
			e.printStackTrace();
		}
	}

	public void createUserWithGroup(Roster roster, String userName, String name, String... groupNames) {
		try {
			roster.createEntry(userName, name, groupNames);
		} catch (NotLoggedInException | NoResponseException | XMPPErrorException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeUser(Roster roster, String userName) {
		try {
			RosterEntry entry = roster.getEntry(userName);
			if (entry != null)
				roster.removeEntry(entry);
			else
				System.out.println(userName + "不是好友");
		} catch (NotLoggedInException | NoResponseException | XMPPErrorException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeUserState(XMPPConnection tcpConnect, int type) {
		Presence presence = new Presence(Presence.Type.available);
		switch (type) {
		case 0:
			presence.setMode(Mode.available);
			presence.setStatus("在线");
			break;
		case 1:
			presence.setMode(Mode.away);
			presence.setStatus("离开");
			break;
		case 2:
			presence.setMode(Mode.chat);
			presence.setStatus("聊天");
			break;
		case 3:
			presence.setMode(Mode.dnd);
			presence.setStatus("忙");
			break;
		case 4:
			presence.setMode(Mode.away);
			presence.setStatus("离开");
			break;
		case 5:
			presence = new Presence(Presence.Type.unavailable);
			break;
		}
		try {
			tcpConnect.sendPacket(presence);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buddyRelate(XMPPConnection tcpConnect, Type type) {
		Presence presence = new Presence(type);
		presence.setTo(receiver + "@" + serviceName);
		try {
			tcpConnect.sendPacket(presence);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		ConnectionConfiguration config = new ConnectionConfiguration(host, 5222, serviceName);
		config.setSecurityMode(SecurityMode.disabled);
		config.setReconnectionAllowed(true);
		XMPPConnection tcpConnect = new XMPPTCPConnection(config);

		try {
			tcpConnect.connect();
			// createUser(tcpConnect, "test123456789", "987654321");
			try {
				userLogin(tcpConnect, sender, password, suffix);
				// changUserPassword(tcpConnect, "123456789");
				// deleteUser(tcpConnect);
			} catch (Exception e) {
			}
			try {
				tcpConnect.addPacketListener(new MyPacketListener(tcpConnect),
						new PacketTypeFilter(Presence.class));
				tcpConnect.addPacketListener(new MyPacketListener(tcpConnect),
						new PacketTypeFilter(Message.class));
				// tcpConnect.addPacketInterceptor(new MyPacketInterceptorB(),
				// new PacketTypeFilter(Message.class));
				// final Roster roster = tcpConnect.getRoster();
				// roster.addRosterListener(new MyRosterListenerB());
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
			Version version = new Version("ackflag", "4.0.0", "ios");
			tcpConnect.sendPacket(version);
			// buddyRelate(tcpConnect, Presence.Type.subscribe);
			// buddyRelate(tcpConnect, Presence.Type.subscribed);
			// 先取消订阅关系，再来订阅
			// buddyRelate(tcpConnect, Presence.Type.unsubscribe);
			// buddyRelate(tcpConnect, Presence.Type.subscribe);
			// 先同意取消订阅，在同意订阅
			// buddyRelate(tcpConnect, Presence.Type.unsubscribed);
			// buddyRelate(tcpConnect, Presence.Type.subscribed);
			// Roster roster = tcpConnect.getRoster();
			// getAllGroupAndUsers(roster);
			// getAllUserAndGroup(roster);
			// createUserNoGroup(roster, "1_jiamin123@im_test", "jiamin");
			// removeUser(roster, "1_jiamin123@im_test");
			// createUserWithGroup(roster, "1_jiamin123@im_test", "jiamin",
			// "测试1", "测试2", "测试3");
			// changeUserState(tcpConnect, 2);
			// message.setType(Message.Type.chat);
			// message.setType(Message.Type.groupchat);
			// message.setType(Message.Type.headline);
			// message.setSubject("hello");
			// System.out.println("123456");
			// Thread.sleep(2000);
			// for(int i = 0; i < 1000; i++) {
			// Thread.sleep(20000000);
			int i = 0;
			// while(true) {
			Message message = new Message();
			message.setSubject("sys");
			message.setFrom(sender + "@" + serviceName);
			// message.setFrom("2_00014f05a59f37a68521d3057970f022" + "@" +
			// serviceName);
			message.setTo(receiver + "@" + serviceName);
			// message.setBody("test" + i++);
			// ExtensionMessage extension = new ExtensionMessage();
			// extension.setColor("red");
			// extension.setFood("rice");
			// message.addExtension(extension);
			message.addBody("" + (new Date().getTime()), "" + i);
			tcpConnect.sendPacket(message);
			Thread.sleep(10);
			// }
			// Element element = new Element("iq");
			// Version version = new Version("ackflag", "6.8.8", "ios");
			// tcpConnect.sendPacket(version);
			// Presence presence =
			// new Presence(Presence.Type.available);
			// presence.setMode(Mode.dnd);
			// // presence.setFrom(sender + "@" + serviceName);
			// presence.setFrom("admin@im.zhe800.com");
			// presence.setTo(receiver + "@" + serviceName);
			// tcpConnect.sendPacket(presence);
			//
			//
			// try {
			// this.sleep(10 * 1000);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// }

			// changeUserState(tcpConnect, 5);
			// tcpConnect.disconnect();

		} catch (SmackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		try {
			// ApplicationContext ctx = new
			// ClassPathXmlApplicationContext("all.xml");
			// redisTemplate = (RedisTemplate)ctx.getBean("redisTemplate");
			ExecutorService executorService = Executors.newFixedThreadPool(100);
			// testSmack s = new testSmack("1_7da3f4ba1878397bb211b242864cc309",
			// "3_13e16fb211c23ad7973345cdfc004793", "-2092536393");
			// testSmack s = new testSmack("3_13e16fb211c23ad7973345cdfc004793",
			// "1_7da3f4ba1878397bb211b242864cc309", "123456");
			// testSmack s = new testSmack("admin",
			// "3_20448f950df240c0b0753ca3ba647240", "123456");
			testSmack s = new testSmack("1_test20150413_00135",
					"3_46471101a2a74c878d918fcd2125287b", "123456");
			executorService.execute(s);
			// tester r = new tester("duheng2", "duheng3", "68");
			// r.start();
			// tester t = new tester("atlantis", "duheng", "310044248");
			// t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	class MyPacketListener implements PacketListener {
		XMPPConnection tcpConnect;
		public MyPacketListener(XMPPConnection tcpConnect) {
			super();
			this.tcpConnect = tcpConnect;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.jivesoftware.smack.PacketListener#processPacket(org.jivesoftware
		 * .smack.packet.Packet)
		 */
		@Override
		public void processPacket(Packet packet) throws NotConnectedException {

			// String message =
			// packet.toString().substring(packet.toString().indexOf("<body>") +
			// 6, packet.toString().indexOf("</body>"));
			// System.out.println(message);
			// if(TestCluster.receiveMaps.containsKey(message)) {
			// TestCluster.receiveMaps.put(message,
			// TestCluster.receiveMaps.get(message) + 1);
			// } else {
			// TestCluster.receiveMaps.put(message, 1);
			// }
			if (packet != null) {
				System.out.println(packet);
				if (packet instanceof Message) {
					try {
						// int time = (new Random()).nextInt(20000);
						// Thread.sleep(time);
						// String id = packet.getPacketID();
						// Ack ack = new Ack(id);
						// tcpConnect.sendPacket(ack);
						// System.out.println("发送ack" + id + ", time = " +
						// time);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	class MyRosterListener implements RosterListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.jivesoftware.smack.RosterListener#entriesAdded(java.util.Collection
		 * )
		 */
		@Override
		public void entriesAdded(Collection<String> addresses) {
			System.out
					.println("public void entriesAdded(Collection<String> addresses)" + addresses);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.jivesoftware.smack.RosterListener#entriesUpdated(java.util.Collection
		 * )
		 */
		@Override
		public void entriesUpdated(Collection<String> addresses) {
			System.out
					.println("public void entriesUpdated(Collection<String> addresses)" + addresses);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.jivesoftware.smack.RosterListener#entriesDeleted(java.util.Collection
		 * )
		 */
		@Override
		public void entriesDeleted(Collection<String> addresses) {
			System.out.println("entriesDeleted(Collection<String> addresses)" + addresses);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.jivesoftware.smack.RosterListener#presenceChanged(org.jivesoftware
		 * .smack.packet.Presence)
		 */
		@Override
		public void presenceChanged(Presence presence) {
			System.out.println("public void presenceChanged(Presence presence) = " + presence);
		}
	}

	/**
	 * @author yinwenjie
	 *
	 */
	class MyPacketInterceptor implements PacketInterceptor {
		@Override
		public void interceptPacket(Packet packet) {
			System.out.println("public void interceptPacket(Packet packet) = " + packet);
		}
	}
}
// }