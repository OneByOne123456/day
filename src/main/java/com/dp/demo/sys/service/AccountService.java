package com.dp.demo.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dp.common.BaseService;
import com.dp.common.utils.util.UserUtil;
import com.dp.demo.sys.mapper.AccountMapper;
import com.dp.demo.sys.pojo.Account;
import com.dp.demo.sys.pojo.User;
@Service
public class AccountService extends BaseService<Account>{
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private UserService userService;
	
	public List<Account> getList(Map<String,Object> map){
		String isSelf = (String) map.get("isSelf");
		List<Account> list = new ArrayList<Account>();
		User user = UserUtil.getCurrentUser();
		if("1".equals(isSelf)){//查询个人
			map.put("userId", user.getId());
		}else{//查询家庭所有的
			if(user.getFamilyId() != null){//判断是否加入家庭
				User u = new User();
				u.setFamilyId(user.getFamilyId());
				List<User> userList = userService.findListByObject(u);
				StringBuffer userId = new StringBuffer("");
				for(User u2 : userList){
					userId.append(",'"+user.getId()+"'");
				}
				if(userId.toString().length() > 0){
					map.put("userId",userId.toString().substring(1));
				}else if(!"".equals(userId.toString())){
					map.put("userId", userId.toString());
				}
			}else{//没有则返回自己
				map.put("userId", user.getId());
			}
		}
		
		return list;
	}
}
