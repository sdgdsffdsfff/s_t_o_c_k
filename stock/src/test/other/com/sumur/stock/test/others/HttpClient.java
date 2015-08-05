package com.sumur.stock.test.others;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class HttpClient {
	/*
	历史数据
	HTML Output: http://finance.yahoo.com/q/hp?s=300072.sz&c=2015&a=7&b=20&f=2015&d=8&e=4	
	CSV Output: http://ichart.finance.yahoo.com/table.csv?s=300072.sz&d=7&e=23&f=2010&a=5&b=11&c=2010
	由于历史原因，也可以用地址 http://table.finance.yahoo.com/table.csv
	s: 股票代码 (e.g. 002036.SZ 300072.SZ 600036.SS 等)
	c-a-b: 起始日期年、月、日 (月份的起始索引为0) 2010-5-11 = 2010年6月11日
	f-d-e: 结束日期年、月、日 (月份的起始索引为0) 2010-7-23 = 2010年8月23日
	g: 时间周期。d=每日，w=每周，m=每月，v=只返回除权数据
	省略所有参数，只制定股票代码时，返回所有历史数据
	“实时”数据
	http://download.finance.yahoo.com/d/quotes.csv?s=300072.SZ+600036.SS&f=spl1d1t1c1ohg (f: 指定返回数据的格式)
	s: 股票代码
	l: 最后成交价格和时间 。l1: 最后成交价格；d1: 最后交易日期；t1: 最后交易时间
	p: 昨日收盘价；o: 开盘价；h: 最高价；g: 最低价；c1: 涨幅
	v: 成交量；a2: 平均每日成交量
	j: 52周最低价；j5: 52周价格变动 j6: 52周涨幅 （相对最低价）
	k: 52周最高价；k4: 52周价格变动 k5: 52周涨幅 （相对最高价）
	m3: MA50；m4: MA20；m5:
*/
	@Test
	public void test() throws IOException{
		long begintime = System.currentTimeMillis();
        
		//"300072.SZ",38.99,40.74,"8/4/2015","2:29pm",+1.75,39.10,41.58,38.20
        URL url = new URL("http://download.finance.yahoo.com/d/quotes.csv?s=300072.SZ&f=spl1d1t1c1ohg");
        //URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s=000001.sz&c=2015&a=7&b=20&f=2015&d=8&e=4");
        HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
        urlcon.connect();         //获取连接
        InputStream is = urlcon.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
        StringBuffer bs = new StringBuffer();
        String l = null;
        while((l=buffer.readLine())!=null){
            bs.append(l).append("\n");
        }
        System.out.println(bs.toString());       
        System.out.println("总共执行时间为："+(System.currentTimeMillis()-begintime)+"毫秒");
	}
}
