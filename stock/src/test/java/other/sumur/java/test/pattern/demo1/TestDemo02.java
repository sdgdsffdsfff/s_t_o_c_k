package other.sumur.java.test.pattern.demo1;

import org.junit.Test;

/**
 * 单例模式的6种实现方式
 * @author Li mao sen
 */
public class TestDemo02 {
	@SuppressWarnings("unused")
	@Test
	public void test1() {
		Single01 single01= Single01.getInstance();
		Single02 single02= Single02.getInstance();
		Single03 single03= Single03.getInstance();
		Single04 single04= Single04.getInstance();
		Single05 single05= Single05.getInstance();
		Single06 single06= Single06.ONE_INSTANCES;
	}
}

/**
 * 线程安全 非延迟加载
 * 
 * @author Li mao sen
 *
 */
class Single01 {
	private static Single01 single = new Single01();

	private Single01() {
	}

	public static Single01 getInstance() {
		return single;
	}
}

/**
 * 非线程安全 延迟加载
 * 
 * @author Li mao sen
 *
 */
class Single02 {
	private static Single02 single;

	private Single02() {
	}

	public static Single02 getInstance() {
		if (single == null) {
			single = new Single02();
		}
		return single;
	}
}

/**
 * 线程安全 延迟加载 效率不高
 * 
 * @author Li mao sen
 *
 */
class Single03 {
	private static Single03 single;

	private Single03() {
	}

	public static synchronized Single03 getInstance() {
		if (single == null) {
			single = new Single03();
		}
		return single;
	}
}

/**
 * 线程安全 延迟加载 双检索
 * 
 * @author Li mao sen
 *
 */
class Single04 {
	private static Single04 single;

	private Single04() {
	}

	public static Single04 getInstance() {
		if (single == null) {
			synchronized (Single04.class) {
				if (single == null) {
					single = new Single04();
				}
			}
		}
		return single;
	}
}

/**
 * 线程安全 延迟加载 登记式 静态内部类
 * 
 * @author Li mao sen
 *
 */
class Single05{
	private Single05(){}
	private static class StaticSingle{
		public static final Single05 single = new Single05();
	}
	public static Single05 getInstance(){
		return StaticSingle.single;
	}
}

/**
 * 线程安全 延迟加载 自动序列化
 * @author Li mao sen
 *
 */
enum Single06{
	ONE_INSTANCES;
}