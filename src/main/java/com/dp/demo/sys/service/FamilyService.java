package com.dp.demo.sys.service;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dp.common.BaseService;
import com.dp.common.utils.util.UserUtil;
import com.dp.demo.sys.pojo.Family;
import com.dp.demo.sys.pojo.User;
@Service
public class FamilyService extends BaseService<Family>{
	
	@Autowired
	private UserService userService;
	
	@Transactional(rollbackFor=Exception.class)
	public void addFamily(Family family){
		User user = UserUtil.getCurrentUser();
		family.setAdminUser(user.getId());
		family.setCreateUser(user.getName());
		family.setCreateTime(new Date());
		family.setUpdateUser(user.getName());
		family.setUpdateTime(new Date());
		this.save(family);
		user.setFamilyId(family.getId());
		user.setIsAdmin(1);
		userService.update(user);
	}
}
