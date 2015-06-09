package com.edgar.core.repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/3.
 */
public interface BaseMapper<T, ID> {

    T selectByPrimaryKey(ID id);

    int insert(T entity);

    int updateByPrimaryKey(T entity);

    int deleteByPrimaryKey(ID id);

    int count(Map<String, Object> params);

    List<T> query(Map<String, Object> params);
}
