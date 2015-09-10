package other.sumur.stock.test.others;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;


public class InnerSqlKey {
	
	@Test
	public void test1(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHH");
    	Calendar calendar = Calendar.getInstance();
    	long key = Integer.parseInt(dateFormat.format(new Date()));
    	//800 = zhe800，100E = 百亿，
    	String inputKey=((key + 800) * 100 / calendar.get(Calendar.HOUR_OF_DAY)) + "E";
    	System.out.println(inputKey);
	}
}
