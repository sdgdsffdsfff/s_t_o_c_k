package com.sumur.stock.dao.custom;

import java.util.List;

import com.sumur.stock.entity.orm.StockData;

public interface StockDataCustom {
	/**
     * 批量插入数据
     * @author Li Mao sen
     * @param list
     */
    void insertBatch(List<StockData> list);
    
    StockData selectById(Long id);
}
