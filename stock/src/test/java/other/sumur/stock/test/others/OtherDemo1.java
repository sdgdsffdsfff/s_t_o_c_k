package other.sumur.stock.test.others;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import net.sf.json.JSONObject;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.junit.Test;

public class OtherDemo1 {
	@Test
	public void test1(){
		System.out.println(Integer.MAX_VALUE);
	}
	
	@Test
	public void test2(){
		JSONObject js = new JSONObject();
		js.put(1, 123);
		js.put("11", "123");
		System.out.println(js);
	}
	
	
	@Test
	public void test3() throws ParseException {
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		formatter1.setTimeZone(TimeZone.getTimeZone("UTC"));

		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		formatter2.setTimeZone(TimeZone.getDefault());
		
		Date d = new Date();
		System.out.println(d.toLocaleString());
		String sd1 = formatter1.format(d);
		String sd2 = formatter2.format(d);
		System.out.println(sd1+"\n"+sd2);
		Date d1 = formatter1.parse(sd1);
		Date d2 = formatter2.parse(sd2);
		System.out.println(d1.toLocaleString()+"\n"+d2.toLocaleString());
		System.out.println(d1.equals(d2));
	}
	
	@Test
	public void test4(){
		ImMessageControl mc = new ImMessageControl();
		mc.setAddList(1);
		mc.setIsShow(1);
		mc.setIsOffline(9);
		mc.setCreateDate(new Date());
		JSONObject jsonmc = JSONObject.fromObject(mc);
		ImMessageControl mc1 = (ImMessageControl) JSONObject.toBean(jsonmc, ImMessageControl.class);
		JSONObject jsonmc1 = JSONObject.fromObject(mc1);
		System.out.println(jsonmc);
		System.out.println(jsonmc1);
		System.out.println(mc.equals(mc1));
		System.out.println(jsonmc.equals(jsonmc1));
		System.out.println(jsonmc==jsonmc1);
	}
	
	@Test
	public void test5(){
		JSONObject o = new JSONObject();
		o.put("123", null);
		Object obj = o.get("123");
		System.out.println(o);
		System.out.println(obj);
	}
	
	@Test
	public void test6(){
		Integer x = 5;
		System.out.println(x.toString());
	}
}
