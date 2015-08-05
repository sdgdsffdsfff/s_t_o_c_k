package com.sumur.stock.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sumur.stock.exception.BizException;
import com.sumur.stock.exception.ResponseCode;

/**
 * 高并发日期处理工具类
 * 
 * @author jiamin
 */
public class DateUtil {
	public final static String FORMAT_DATE = "yyyy-MM-dd";
	public final static String FORMAT_TIME = "HH:mm:ss";
	public final static String FORMAT_DATEMINUTE = "yyyy-MM-dd HH:mm";
	public final static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

	public final static String FORMAT_DATE_ZH = "yyyy年MM月dd日";
	public final static String FORMAT_DATETIME_ZH = "yyyy年MM月dd日 HH时mm分ss秒";

	private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();
	private static final Object object = new Object();

	/**
	 * 获取SimpleDateFormat
	 * @param pattern 日期格式
	 * @return SimpleDateFormat对象
	 */
	private static SimpleDateFormat getDateFormat(String pattern) {
		SimpleDateFormat dateFormat = threadLocal.get();
		if (dateFormat == null) {
			synchronized (object) {
				if (dateFormat == null) {
					dateFormat = new SimpleDateFormat(pattern);
					dateFormat.setLenient(false);
					threadLocal.set(dateFormat);
				}
			}
		}
		dateFormat.applyPattern(pattern);
		return dateFormat;
	}

	/**
	 * 将日期字符串转化为日期,日期字符串为空返回null
	 * @param dateStr 日期字符串
	 * @param pattern 日期格式
	 * @return 格式化后的日期
	 * @throws BizException 日期格式化错误异常
	 */
	public static Date parseDate(String dateStr, String pattern) throws BizException {
		Date formatDate = null;
		if (dateStr != null && !dateStr.trim().equals("")) {
			try {
				formatDate = getDateFormat(pattern).parse(dateStr);
			} catch (Exception e) {
				throw new BizException("字符串转化成日期异常", ResponseCode._402);
			}
		}
		return formatDate;
	}

	/**
	 * 将日期转化为日期字符串，日期为null返回字符串null
	 * @param date 日期
	 * @param pattern 日期格式
	 * @return 日期字符串
	 * @throws BizException 
	 */
	public static String formatDate(Date date, String pattern) throws BizException {
		String formatDateStr = null;
		if (date != null) {
			try {
				formatDateStr = getDateFormat(pattern).format(date);
			} catch (Exception e) {
				throw new BizException("日期转化成字符串异常", ResponseCode._402);
			}
		}
		return formatDateStr;
	}
	
	/**
	 * 将时间戳转化成,指定格式的日期 date
	 * @param date 日期是时间戳  长整型
	 * @param pattern 日期格式
	 * @return 日期字符串
	 * @throws BizException 
	 */
	public static String formatDate(long date, String pattern) throws BizException {
		try {
			return getDateFormat(pattern).format(date);
		} catch (Exception e) {
			throw new BizException("日期转化成字符串异常", ResponseCode._402);
		}
	}
	
	/**
	 * 获得当天的起始时间
	 * @param date 例如 2015-02-01 21:03:51
	 * @return	2015-02-01 00:00:00
	 * @throws BizException 
	 */
	public static Date getEarlyDate(Date date) throws BizException{
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.HOUR, 0);
		return ca.getTime();
	}
	
	/**
	 * 获得当天的结束时间
	 * @param date 例如 2015-02-01 21:03:51
	 * @return	2015-02-01 23:59:59
	 */
	public static Date getLaterDate(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.SECOND, 59);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.HOUR, 23);
		return ca.getTime();
	}
}
