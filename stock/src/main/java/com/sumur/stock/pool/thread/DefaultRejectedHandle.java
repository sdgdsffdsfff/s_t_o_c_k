package com.sumur.stock.pool.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.stereotype.Component;
/**
 * 
 * @author Li mao sen
 *
 */
@Component
public class DefaultRejectedHandle implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println(r.getClass().getName());
	}

}
