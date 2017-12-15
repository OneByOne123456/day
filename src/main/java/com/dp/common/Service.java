package com.dp.common;

import java.util.List;


import tk.mybatis.mapper.entity.Condition;

public interface Service<T> {
	int save(T model);//持久化
    int save(List<T> models);//批量持久化
    int deleteById(Object id);//通过主鍵刪除
    int delete(T model);
    int update(T model);//更新(为空的为null)
    int update(List<T> models);//更新(为空的为null)
    int updateNotNull(T model);//更新(为空的不更新)
    int updateNotNull(List<T> models);//批量更新(为空的不更新)
    T findById(Object id);//通过ID查找
    T findByObject(T model);//通过对象查找
    List<T> findAll();//获取所有


}
