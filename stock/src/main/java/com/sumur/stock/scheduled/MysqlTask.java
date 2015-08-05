package com.sumur.stock.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.sumur.stock.dao.mapper.StockUserMapper;

/**
 * 用于定时检测mysql连接,不然mysql连接池会有8小时超时错误
 * @author Li mao sen
 *
 */
public class MysqlTask {
	@Autowired
	private StockUserMapper stockUserMapper;

	@Scheduled(fixedRate=7200000)
	public void updateDateSource(){
		stockUserMapper.selectByPrimaryKey(0L);
	}
}
