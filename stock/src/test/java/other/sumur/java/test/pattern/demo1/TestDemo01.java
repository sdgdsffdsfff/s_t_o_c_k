package other.sumur.java.test.pattern.demo1;

import org.junit.Test;

/**
 * 工厂模式,抽象工厂模式
 * @author Li mao sen
 *
 */
public class TestDemo01 {
	@Test
	public void test1(){
		FactoryProducer fp = new FactoryProducer();
		AbtractFactory af = fp.getFacroty("color");
		color c = af.getColor("red");
		c.show();
	}
}

interface color{
	public void show();
}

class red implements color{
	public void show() {
		System.out.println("red");
	}
}

class yellow implements color{
	public void show() {
		System.out.println("yellow");
	}
}

interface Shape{
	public void display();
}

class circle implements Shape{
	public void display(){
		System.out.println("circle");
	}
}

class square implements Shape{
	public void display(){
		System.out.println("square");
	}
}

interface AbtractFactory{
	public color getColor(String name);
	public Shape getShape(String name);
}

class AbtractColorFactory implements AbtractFactory{
	public color getColor(String name){
		if("red".equals(name)){
			return new red();
		}else{
			return new yellow();
		}
	}

	public Shape getShape(String name) {
		return null;
	}
}

class AbtractShapeFactory implements AbtractFactory{
	public Shape getShape(String name){
		if("circle".equals(name)){
			return new circle();
		}else{
			return new square();
		}
	}

	public color getColor(String name) {
		return null;
	}
}

class FactoryProducer{
	public AbtractFactory getFacroty(String name){
		if("color".equals(name)){
			return new AbtractColorFactory();
		}else{
			return new AbtractShapeFactory();
		}
	}
}

