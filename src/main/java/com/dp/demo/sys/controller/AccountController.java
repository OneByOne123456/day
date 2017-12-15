package com.dp.demo.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dp.common.AjaxResult;
import com.dp.demo.sys.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
	private static Logger log = Logger.getLogger(AccountController.class);
	@Autowired
	private AccountService accountService;
	public String page(HttpServletRequest request){
		return "/static/html/account";
	}
}
