package com.edgar.mybatis;

public interface BaseMapper<T, ID> {

    T selectByPrimaryKey(ID id);
}