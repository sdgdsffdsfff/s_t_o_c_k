package com.sumur.stock.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumur.stock.dao.mapper.StockCompanyMapper;
import com.sumur.stock.dao.mapper.StockDataMapper;
import com.sumur.stock.entity.orm.StockCompany;
import com.sumur.stock.entity.orm.StockCompanyExample;
import com.sumur.stock.entity.orm.StockData;
import com.sumur.stock.exception.BizException;
import com.sumur.stock.service.StockDataService;
import com.sumur.stock.util.DateUtil;

@Service
@Transactional
public class StockDataServiceImpl implements StockDataService {
	
	@Autowired
	private StockCompanyMapper stockCompanyMapper;
	
	@Autowired
	private StockDataMapper stockDataMapper;
	

	@Override
	public void initData() {
		StockCompanyExample scExample = new StockCompanyExample();
		scExample.createCriteria();
		List<StockCompany> comps = stockCompanyMapper.selectByExample(scExample);
		
		String url = "http://download.finance.yahoo.com/d/quotes.csv?s=";
		for(StockCompany comp : comps){
			String cpsCode =  comp.getCode();
			List<String> list = sax(url);
			list.remove(0);
			for(String str:list){
				StockData sd = string2StockData(str);
				sd.setCode(cpsCode);
				stockDataMapper.insertSelective(sd);
			}
		}
	}

	@Override
	public void saveYesterdayData() {
		// TODO Auto-generated method stub

	}

	@Override
	public StockData getTodayData() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<String> sax(String urlLink) {
		HttpURLConnection urlcon = null;
		InputStream ins = null;
		BufferedReader buffer = null;
		List<String> results = new ArrayList<String>();
		try {
			URL url = new URL(urlLink);
			urlcon = (HttpURLConnection) url.openConnection();
			urlcon.connect(); // 获取连接
			ins = urlcon.getInputStream();
			buffer = new BufferedReader(new InputStreamReader(ins));
			String l = null;
			while ((l = buffer.readLine()) != null) {
				results.add(l);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			urlcon.disconnect();
		}
		return results;
	}
	
	private StockData string2StockData(String str){
		StockData sd = new StockData();
		String[] arr = str.split(",");
		if(arr.length!=7){
			return null;
		}else{
			try {
				sd.setDate(DateUtil.parseDate(arr[0], DateUtil.FORMAT_DATE));
			} catch (BizException e) {
				e.printStackTrace();
			}
			sd.setOpen(new BigDecimal(arr[1]));
			sd.setHigh(new BigDecimal(arr[2]));
			sd.setLow(new BigDecimal(arr[3]));
			sd.setClose(new BigDecimal(arr[4]));
			sd.setVolume(Long.parseLong(arr[5]));
			sd.setAdj(new BigDecimal(arr[6]));
		}
		return sd;
	}
}
