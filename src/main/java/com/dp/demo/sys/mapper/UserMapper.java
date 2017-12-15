package com.dp.demo.sys.mapper;

import org.springframework.stereotype.Repository;

import com.dp.demo.sys.pojo.User;

import tk.mybatis.mapper.common.Mapper;
@Repository
public interface UserMapper extends Mapper<User>{

}
