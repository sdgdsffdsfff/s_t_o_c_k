package com.sumur.stock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sumur.stock.dao.mapper.StockCompanyMapper;
import com.sumur.stock.entity.orm.StockCompany;
import com.sumur.stock.service.StockCompanyService;

@Service
public class StockCompanyServiceImpl implements StockCompanyService {
	@Autowired
	private StockCompanyMapper stockCompanyMapper;
	
	@Override
	public void addCompany(StockCompany record) {
		stockCompanyMapper.insertSelective(record);
	}
}
