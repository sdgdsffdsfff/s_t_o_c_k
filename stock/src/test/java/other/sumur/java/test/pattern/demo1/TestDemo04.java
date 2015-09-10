package other.sumur.java.test.pattern.demo1;

import java.util.HashMap;

import org.junit.Test;

/**
 * 原型模式,创建对象通过clone,而不是new,不如部分对象存在于数据库中,不至于每次创建都从数据库获取.
 * 可以先建立一个对象缓存列表,每当要创建对象时,从类表中找出对象,并克隆一个.
 * 原型模式往往和工厂模式联合使用
 * @author Li mao sen
 *
 */
public class TestDemo04 {
	@Test
	public void test1() throws CloneNotSupportedException{
		Person man = PersonCache.getOne("man");
		Person woman = PersonCache.getOne("woman");
		System.out.println(man.getSex());
		System.out.println(woman.getSex());
	}
}


abstract class Person implements Cloneable{
	public int age;
	public String name;
	abstract public String getSex();
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}

class Man extends Person{
	@Override
	public String getSex() {
		return "man";
	}
}

class Woman extends Person{
	@Override
	public String getSex() {
		return "woman";
	}
}

class PersonCache{
	private static HashMap<String,Person> map = new HashMap<String, Person>();
	static{
		map.put("man", new Man());
		map.put("woman", new Woman());
	}
	
	public static Person getOne(String type) throws CloneNotSupportedException{
		Person p = map.get(type);
		return (Person) p.clone();
	}
}