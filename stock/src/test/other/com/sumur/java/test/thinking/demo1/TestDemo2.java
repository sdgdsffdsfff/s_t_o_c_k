package com.sumur.java.test.thinking.demo1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Test;

public class TestDemo2 {
	/**
	 * 克隆方式一.类中所有的其他类组合都实现Cloneable接口, 同时在clone方法中写明所有其他类的clone方式
	 * 
	 * @throws CloneNotSupportedException
	 */
	@Test
	public void test1() throws CloneNotSupportedException {
		Teacher t1 = new Teacher();
		Student s1 = new Student();
		t1.name = "t1";
		s1.teacher = t1;
		s1.name = "s1";

		Student s2 = (Student) s1.clone();
		s2.teacher.name = "t2";
		s2.name = "s2";
		System.out.println(s1.name);
		System.out.println(s1.teacher.name);
		System.out.println(s2.name);
		System.out.println(s2.teacher.name);
	}

	/**
	 * 克隆方式二.使用流进行克隆,基本思想为: clone方法中将对象序列化,再将结果反序列化为新的对象
	 * 
	 * @throws CloneNotSupportedException
	 */
	@Test
	public void test2() throws CloneNotSupportedException {
		Teacher t1 = new Teacher();
		Person s1 = new Person();
		t1.name = "t1";
		s1.teacher = t1;
		s1.name = "s1";

		Person s2 = (Person) s1.clone();
		s2.teacher.name = "t2";
		s2.name = "s2";
		System.out.println(s1.name);
		System.out.println(s1.teacher.name);
		System.out.println(s2.name);
		System.out.println(s2.teacher.name);
	}
}

class Teacher implements Cloneable, Serializable {
	public String name;

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

class Student implements Cloneable {
	public String name;
	public Teacher teacher;

	public Object clone() throws CloneNotSupportedException {
		Student s = (Student) super.clone();
		s.teacher = (Teacher) s.teacher.clone();
		return s;
	}
}

class Person implements Cloneable, Serializable {
	public String name;
	public Teacher teacher;

	public Object clone() throws CloneNotSupportedException {
		Object o = null;
		try {
			// 此处的输入输出流也可以是文件流
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(this);
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			o = oi.readObject();
			oo.close();
			bo.close();
			oi.close();
			bi.close();
		} catch (Exception e) {
		}
		return o;
	}

}