package com.dp.demo.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dp.common.BaseService;
import com.dp.common.Constant;
import com.dp.common.utils.util.MD5Util;
import com.dp.common.utils.util.Md5;
import com.dp.common.utils.util.UserUtil;
import com.dp.demo.sys.pojo.Family;
import com.dp.demo.sys.pojo.User;

@Service
public class UserService extends BaseService<User>{
	private static Logger log = Logger.getLogger(UserService.class);
	@Autowired
	private FamilyService familyService;
	public List<User> queryAllUser(){
		List<User> list = super.findAll();
		return list;
	}
	public User getByUsername(String name){
		User user = new User();
		user.setName(name);
		User u = super.findByObject(user);
		return u;
	}
	public void register(User user){
		String name = user.getName();
		User user1 = new User();
		user1.setName(name);
		User user2 = super.findByObject(user1);
		if(user2 != null){
			throw new IllegalArgumentException("账号已经被申请！");
		}else{
			String pw = MD5Util.md5(name,user.getPassword());
			user.setPassword(pw);
			Date date = new Date();
			user.setCreateTime(date);
			user.setUpdateTime(date);
			super.save(user);
		}
	}
	public Map<String,Object> userData(){
		User user = this.findById(UserUtil.getCurrentUser().getId());
		Family family = new Family();
		String admin = "";
		List<User> familyUserList = new ArrayList<User>();
		if(!StringUtils.isEmpty(user.getFamilyId())){
			family = familyService.findById(user.getFamilyId());
			User u = this.findById(family.getAdminUser());
			admin = u.getRealName();
			User u2 = new User();
			u2.setFamilyId(family.getId());
			u2.setIsAdmin(1);
			List<User> list1 = this.findListByObject(u2);
			u2.setIsAdmin(2);
			List<User> list2 = this.findListByObject(u2);
			u2.setIsAdmin(0);
			List<User> list3 = this.findListByObject(u2);
			familyUserList.addAll(list1);
			familyUserList.addAll(list2);
			familyUserList.addAll(list3);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("family", family);
		map.put("admin", admin);
		map.put("familyUserList", familyUserList);
		return map;
	}
}
