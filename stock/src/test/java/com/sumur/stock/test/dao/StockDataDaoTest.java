package com.sumur.stock.test.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sumur.stock.dao.custom.StockDataCustom;
import com.sumur.stock.entity.orm.StockData;
import com.sumur.stock.test.base.BaseTestCase;

public class StockDataDaoTest extends BaseTestCase{
	@Autowired
	private StockDataCustom stockDataCustom;
	
	@Test
	public void test(){
		StockData sd = stockDataCustom.selectById(12837L);
		System.out.println(sd.getCode());
	}
}
