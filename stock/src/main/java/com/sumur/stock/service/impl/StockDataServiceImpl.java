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
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumur.stock.dao.custom.StockDataCustom;
import com.sumur.stock.dao.mapper.StockCompanyMapper;
import com.sumur.stock.dao.mapper.StockDataMapper;
import com.sumur.stock.entity.orm.StockCompany;
import com.sumur.stock.entity.orm.StockCompanyExample;
import com.sumur.stock.entity.orm.StockData;
import com.sumur.stock.entity.orm.StockDataExample;
import com.sumur.stock.exception.BizException;
import com.sumur.stock.service.StockDataService;
import com.sumur.stock.util.DateUtil;
import com.sumur.stock.util.LoggerUtil;

@Service
public class StockDataServiceImpl implements StockDataService {
	protected static final Logger logger = LoggerUtil.getLogger(StockDataServiceImpl.class);
	//批量插入数据时,单次插入数据条数
	private static final Integer INSERT_TEMP = 200;
	//连接失败后重试次数
	private static final Integer TRY_TIME = 3;
	
	@Autowired
	private StockCompanyMapper stockCompanyMapper;

	@Autowired
	private StockDataMapper stockDataMapper;
	
	@Autowired
	private StockDataCustom stockDataCustom;

	@Autowired
	private ThreadPoolTaskExecutor initDataTaskExecutor;

	@Override
	public void initData() {
		// 先清理历史数据
		StockDataExample sdExample = new StockDataExample();
		sdExample.createCriteria();
		stockDataMapper.deleteByExample(sdExample);

		// 重新写如数据
		StockCompanyExample scExample = new StockCompanyExample();
		scExample.createCriteria();
		List<StockCompany> comps = stockCompanyMapper.selectByExample(scExample);
		for (StockCompany comp : comps) {
			// 线程池处理
			InitDataTask task = new InitDataTask(comp);
			initDataTaskExecutor.execute(task);
			logger.info(" ----- One company init sucess -----" + comp.getName());
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

	class InitDataTask implements Callable<StockData> ,Runnable{
		private String companyCode;
		private String companyExt;
		private static final String YOOH_URL = "http://ichart.finance.yahoo.com/table.csv?s=";

		public InitDataTask(StockCompany c) {
			this.companyCode = c.getCode();
			this.companyExt = c.getExt();
		}

		@Override
		public StockData call() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		@Override
		@Transactional
		public void run() {
			List<String> list = sax(YOOH_URL + companyCode + "." + companyExt);
			if (list == null || list.size() <= 1) {
				return;
			}
			list.remove(0);
			List<StockData> sdList = new ArrayList<StockData>();
			for (String str : list) {
				StockData sd = string2StockData(str);
				sd.setCode(companyCode);
				sdList.add(sd);
			}
			//批量插数据
			if (sdList.size() > 0) {
				int index = 0;
				while ((index + INSERT_TEMP) < sdList.size()) {
					stockDataCustom.insertBatch(sdList.subList(index, index + INSERT_TEMP));
					index += INSERT_TEMP;
				}
				stockDataCustom.insertBatch(sdList.subList(index, sdList.size()));
			}
		}

		private StockData string2StockData(String str) {
			StockData sd = new StockData();
			String[] arr = str.split(",");
			if (arr.length != 7) {
				return null;
			} else {
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
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (buffer != null) {
					try {buffer.close();} 
					catch (IOException e) {e.printStackTrace();}
				}
				if (ins != null) {
					try {ins.close();} 
					catch (IOException e) {e.printStackTrace();}
				}
				urlcon.disconnect();
			}
			return results;
		}

		
	}

}
