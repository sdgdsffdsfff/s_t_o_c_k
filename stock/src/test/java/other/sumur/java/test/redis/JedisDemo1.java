package other.sumur.java.test.redis;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class JedisDemo1 {
	private static Jedis jedis;
	private static ShardedJedis sharding;
	private static ShardedJedisPool pool;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		List<JedisShardInfo> shards = Arrays.asList(new JedisShardInfo("localhost", 6379), new JedisShardInfo("localhost", 6379)); // 使用相同的ip:port,仅作测试
		jedis = new Jedis("localhost");
		sharding = new ShardedJedis(shards);
		pool = new ShardedJedisPool(new JedisPoolConfig(), shards);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		jedis.disconnect();
		sharding.disconnect();
		pool.destroy();
	}

	@Test
	public void test1() throws InterruptedException {
		final JedisPubSub jps = new JedisPubSubImpl();

		// 订阅线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				jedis.psubscribe(jps, new String[] { "hello*" });
			}
		}).start();

		// 发布线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					jedis.publish("nihao", "123");
					jedis.publish("hello1", "hello1");
					jedis.publish("hello2", "hello2");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		Thread.sleep(10000);
		// 取消订阅线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				jps.unsubscribe(new String[] { "hello1" });
			}
		}).start();

		Thread.sleep(1000000);
	}

}

class JedisPubSubImpl extends JedisPubSub {
	public void onPMessage(String pattern, String channel, String message) {
		System.out.println(pattern + "=" + channel + "=" + message);
	}
}