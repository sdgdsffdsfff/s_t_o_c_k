package other.sumur.java.test.jvm;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

public class TestDemo01 {
//	类加载过程:
//		加载,验证,准备,解析,初始化,使用,卸载  七个步骤.
//	有且仅有下面四种情况需要立即对类进行初始化:
//		1.遇到new, getstatic, putstatic, invokestatic四条字节码指令时,如果类没有初始化,则必须先触发该类的初始化.
//	生成这四条指令最常见的情况如下:
//	1.1使用new实例化对象
//	1.2读取或修改类中的静态变量(被final修饰,编译期已被放入常量池的除外)
//	1.3调用类中的静态方法
//	2.使用java.lang.reflect包中的方法对类进行反射调用的时候,如果类没有初始化
//	3.如果初始化导出类时,基类没有初始化,需要先初始化基类(此处接口略有不同,当初始化接口时,其父接口并不需要全都初始化,只有当真正用到其父接口时才初始化,例如引用接口中定义的常量.)
//	4.虚拟机启动时,需要指定一个需要执行的主类(包含main方法),该类需要首先被初始化

	@Test
	public void test1(){
		System.out.println(Sub.value);
	}
	
	@Test
	public void test2(){
		DateTime dt = new DateTime();
		dt = dt.plusDays(2);
		Date d = dt.toDate();
		System.out.println(dt.toString("E yyyy-MM-dd HH:mm:ss.SSS"));
	}
	
	@Test
	public void test3(){
		long x = 1;
		long temp = 100000000000L;
		while(++x != temp){}
		System.out.println(x);
	}
}

class Supper{
	public static int value = 123;
	static{
		System.out.println("super static");
	}
	{
		System.out.println("super not static");
	}
	Supper(){
		System.out.println("supper");
	}
}

class Sub extends Supper{
	static{
		System.out.println("sub st");
	}
	{
		System.out.println("sub not st");
	}
}

interface InA{
	public static final int x = 1;
}

interface InB extends InA{
	
}