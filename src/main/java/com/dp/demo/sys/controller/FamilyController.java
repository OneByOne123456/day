package com.dp.demo.sys.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dp.common.AjaxResult;
import com.dp.common.utils.util.UserUtil;
import com.dp.demo.sys.pojo.Family;
import com.dp.demo.sys.pojo.User;
import com.dp.demo.sys.service.FamilyService;

@Controller
@RequestMapping("/family")
public class FamilyController {
	private static Logger log = Logger.getLogger(FamilyController.class);
	@Autowired
	private FamilyService familyService;
	
	@RequestMapping("/addFamily.do")
	@ResponseBody
	public AjaxResult addFamily(Family family){
		try {
			familyService.addFamily(family);
			return new AjaxResult().success();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return new AjaxResult().error();
		}
	}
}
