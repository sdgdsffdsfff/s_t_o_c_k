package com.sumur.stock.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.sumur.stock.entity.other.JsonEntity;
import com.sumur.stock.exception.BizException;
import com.sumur.stock.exception.ResponseCode;
import com.sumur.stock.util.JSONUtil;
import com.sumur.stock.util.LoggerUtil;

/**
 * 所有controller的父级类，里面的工具方法可以是开发人员轻松的构建control层的代码。特别是基于json格式的http接口
 * 
 */
@Controller
public class BaseController {

	protected static final Logger logger = LoggerUtil.getLogger(BaseController.class);

	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/**
	 * 增加全局异常捕捉，避免忘记异常，造成前端页面显示错误
	 * 
	 * @param response
	 * @param ex 需要捕获的异常
	 */
	@ExceptionHandler(Exception.class)
	public void exceptionHandler(HttpServletResponse response, Exception ex) throws AccessDeniedException {
		ex.printStackTrace();
		if (ex instanceof AccessDeniedException) {
			throw (AccessDeniedException) ex;
		}
		logger.error("global error:", ex);
		JsonEntity result = new JsonEntity();
		result.setResponsecode(ResponseCode._501);
		result.setData("");
		result.setErrorinfo("服务器内部异常");
		this.writeResponseMsg(response, result);
	}

	/**
	 * 业务相关的异常
	 * 
	 * @param response
	 * @param ex
	 */
	@ExceptionHandler(BizException.class)
	public void bizExceptionHandler(HttpServletResponse response, BizException ex) {
		ex.printStackTrace();
		logger.warn("biz error:" + ex.getMessage());
		JsonEntity result = new JsonEntity();
		result.setResponsecode(ex.getResponseCode());
		result.setData("");
		result.setErrorinfo(ex.getMessage());
		this.writeResponseMsg(response, result);
	}

	/**
	 * 向response中写入字符串, 该方法是成功是调用
	 * 
	 * @param response HttpServletResponse
	 * @param data 成功写入json data里面数据
	 */
	protected void writeSuccessResponseMsg(HttpServletResponse response, Object data) {
		JsonEntity result = new JsonEntity();
		result.setData(data);
		result.setResponsecode(ResponseCode._200);
		this.writeResponseMsg(response, result);
	}

	/**
	 * 向response中写入字符串, 该方法是成功是调用
	 * 
	 * @param response HttpServletResponse
	 * @param data 成功写入json data里面数据
	 * @param filterProperties 需要进行过滤的，即不进行页面显示的json属性
	 */
	protected void writeSuccessResponseMsg(HttpServletResponse response, Object data, String... filterProperties) {
		JsonEntity result = new JsonEntity();
		result.setData(data);
		result.setResponsecode(ResponseCode._200);
		this.writeResponseMsg(response, result, filterProperties);
	}

	/**
	 * 向response中写入字符串,一般返回错误信息的时候，会使用该打印方法
	 * 
	 * @param response HttpServletResponse
	 * @param msg 写入的字符串
	 */
	protected void writeResponseMsg(HttpServletResponse response, JsonEntity jsonEntity) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		String jsonstr = JSONUtil.toString(jsonEntity);
		out.print(jsonstr);
	}

	/**
	 * 向response中写入字符串,一般返回错误信息的时候，会使用该打印方法
	 * 
	 * @param response HttpServletResponse
	 * @param jsonEntity
	 * @param filterProperties 需要进行过滤的，即不进行页面显示的json属性
	 * @throws IOException
	 */
	protected void writeResponseMsg(HttpServletResponse response, JsonEntity jsonEntity, String... filterProperties) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		String jsonstr = JSONUtil.toString(jsonEntity, filterProperties);
		out.print(jsonstr);
	}
}
