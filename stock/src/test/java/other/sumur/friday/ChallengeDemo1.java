package other.sumur.friday;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class ChallengeDemo1 implements Runnable{
	
	@Override
	public void run() {
		
	}
	
	public void work(){
		ExecutorService es = Executors.newFixedThreadPool(10);
	}
	
	
	void print(Object o) throws Exception{
		//模拟一个1%可能性出现的异常
		int x = new Random().nextInt(100);
		if(x == 20){
			throw new Exception("随机异常");
		}
		//打印
		System.out.println(o.toString());
	}

	public static void main(String[] args) {
		
	}
	
}
