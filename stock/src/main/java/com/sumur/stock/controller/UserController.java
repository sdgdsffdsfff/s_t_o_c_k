package com.sumur.stock.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sumur.stock.entity.orm.StockUser;
import com.sumur.stock.service.StockUserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private StockUserService stockUserService;

	// 控制页面跳转
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(@RequestParam(value = "error", required = false) boolean error, ModelMap model) {
		logger.debug("Received request to show login page");
		if (error == true) {
			model.put("error", "输入的用户名或密码不正确!");
		} else {
			model.put("error", "");
		}
		return "login";
	}
	
	@RequestMapping(value = "/registerPage", method = RequestMethod.GET)
	public String getRegisterPage() {
		logger.debug("Received request to show register page");
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(
			HttpServletRequest request,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "nickName", required = true) String nickName) {
			StockUser user = new StockUser();
			user.setUsername(username);
			user.setNickname(nickName);
			user.setPassword(password);
			stockUserService.insert(user);
			request.getSession().setAttribute("loginUser", user);
			return "redirect:/file/search?currentPage=1";
	}

	
}
