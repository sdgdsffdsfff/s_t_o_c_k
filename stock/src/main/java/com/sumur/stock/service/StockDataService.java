package com.sumur.stock.service;

import com.sumur.stock.entity.orm.StockData;

public interface StockDataService {
	/**
	 * 初始化所有历史数据 到数据库中
	 */
	void initData();
	
	/**
	 * 存储昨日数据,可能由定时任务运行
	 */
	void saveYesterdayData();
	
	/**
	 * 获取今日实时数据
	 * @return
	 */
	StockData getTodayData();
}
