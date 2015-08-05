package com.sumur.stock.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sumur.stock.service.StockDataService;

@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {
	@Autowired
	private StockDataService stockDataService;
	
	@RequestMapping("/init")
	@Secured("ROLE_ADMIN")
	public void init(HttpServletResponse response){
		stockDataService.initData();
		this.writeSuccessResponseMsg(response, "OK");
	}
}
