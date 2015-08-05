package com.sumur.stock.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sumur.stock.dao.mapper.StockUserMapper;
import com.sumur.stock.entity.orm.StockUser;
import com.sumur.stock.service.StockUserService;

@Service
public class StockUserServiceImpl implements StockUserService {
	@Autowired
	private StockUserMapper stockUserMapper;
	
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return stockUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(StockUser record) {
		if(StringUtils.isNotBlank(record.getPassword())){
			Md5PasswordEncoder md5encode = new Md5PasswordEncoder();
			record.setPassword(md5encode.encodePassword(record.getPassword(), record.getUsername()));
		}
		return stockUserMapper.insertSelective(record);
	}

	@Override
	public StockUser selectByPrimaryKey(Long id) {
		return stockUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(StockUser record) {
		if(StringUtils.isNotBlank(record.getPassword())){
			Md5PasswordEncoder md5encode = new Md5PasswordEncoder();
			record.setPassword(md5encode.encodePassword(record.getPassword(), record.getUsername()));
		}
		return stockUserMapper.updateByPrimaryKeySelective(record);
	}

}
