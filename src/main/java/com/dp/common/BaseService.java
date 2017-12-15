package com.dp.common;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Condition;


public class BaseService<T> implements Service<T>{
	@Autowired
	protected Mapper<T> mapper;
	
	private Class<T> modelClass;    // 当前泛型真实类型的Class
	

	public BaseService() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];

	}

	public int save(T model) {
		 return mapper.insertSelective(model);
		
	}

	public int save(List<T> models) {
		int i = 0;
		for(T t:models){
			i=i + mapper.insertSelective(t);
		}
		return i;
	}

	public int deleteById(Object id) {
		return mapper.deleteByPrimaryKey(id);
	}
	
	public int delete(T model){
		return mapper.delete(model);
	}
	
	public int update(T model){
		return mapper.updateByPrimaryKey(model);
	}
	
	public int update(List<T> models){
		int i = 0;
		for(T t: models){
			i=i+mapper.updateByPrimaryKey(t);
		}
		return i;
	}
	
	public int updateNotNull(T model) {
		 return mapper.updateByPrimaryKeySelective(model);
	}
	
	public int updateNotNull(List<T> models){
		int i = 0;
		for(T t:models){
			i = i + mapper.updateByPrimaryKeySelective(t);
		}
		return i;
	}

	public T findById(Object id) {
		return mapper.selectByPrimaryKey(id);
	}

	public T findByObject(T model) throws TooManyResultsException {
		return mapper.selectOne(model);
	}

	public List<T> findAll() {
		return mapper.selectAll();
	}
	public List<T> findListByObject(T model){
		return mapper.select(model);
	}

	
	


}
