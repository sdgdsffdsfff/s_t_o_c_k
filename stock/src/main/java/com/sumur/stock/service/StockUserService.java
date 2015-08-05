package com.sumur.stock.service;

import com.sumur.stock.entity.orm.StockUser;


public interface StockUserService {
	public  int deleteByPrimaryKey(Long id);

	public int insert(StockUser record);

	public StockUser selectByPrimaryKey(Long id);
    
	public int updateByPrimaryKey(StockUser record);
	
}
