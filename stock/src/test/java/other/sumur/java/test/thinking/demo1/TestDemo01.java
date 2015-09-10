package other.sumur.java.test.thinking.demo1;

import org.junit.Test;

public class TestDemo01 {
	@Test
	public void test1(){
		long s = System.currentTimeMillis();
		tone();
		long e = System.currentTimeMillis();
		ttwo();
		long l = System.currentTimeMillis();
		System.out.println("one执行时间:"+(e-s));
		System.out.println("two执行时间:"+(l-e));
	}
	
	/**
	 * StringBuffer,StringBuilder都继承了AbstractStringBuilder.
	 * 而且这两者的append方法都是调用的supper.append.
	 * 所以StringBuffer与StringBuilder的append方法是相同的.
	 * 但是StringBuffer在append方法上加了synchronized.(线程同步,效率低)
	 */
	@Test
	public void test2(){
		StringBuffer sbuffer = new StringBuffer();
		StringBuilder sbuilder = new StringBuilder();
		sbuffer.append("123");
		sbuilder.append("123");
	}
	
	@Test
	public void test3(){
		int a = 1;
		if(a<1)
			if(a>0)
				System.out.println("ifa");
			System.out.println("if out");
	}
	
	void tone(){
		String str = null;
		for(int i = 0;i<900000000;i++){
			str = "sdf"+"sdfs";
		}
	}
	
	void ttwo(){
		String a = "sdf";
		String b = "sdfs";
		String str = null;
		for(int i = 0;i<100000000;i++){
			str = a+b;
		}
	}
}
