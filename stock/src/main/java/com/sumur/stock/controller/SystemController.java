package com.sumur.stock.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sumur.stock.service.StockDataService;

@Controller
public class SystemController extends BaseController {
	@Autowired
	private StockDataService stockDataService;
	
	@RequestMapping("/")
	@ResponseBody
	public String index(HttpServletResponse response){
		return "WELCOME";
	}
	
	@RequestMapping("/system/init")
	@Secured("ROLE_ADMIN")
	public void init(HttpServletResponse response){
		stockDataService.initData();
		this.writeSuccessResponseMsg(response, "OK");
	}
}
