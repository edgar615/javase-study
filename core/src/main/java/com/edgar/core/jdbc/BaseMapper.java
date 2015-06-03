package com.edgar.core.jdbc;

/**
 * Created by Administrator on 2015/6/3.
 */
public interface BaseMapper<T, ID> {

    T selectByPrimaryKey(ID id);
}
