package com.dp.demo.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dp.demo.sys.pojo.Account;

import tk.mybatis.mapper.common.Mapper;
@Repository
public interface AccountMapper extends Mapper<Account>{
	List<Account> getListByParam(Map<String,Object> map);
}
