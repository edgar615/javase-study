package com.edgar.core.repository.transaction;

/**
 * Created by Administrator on 2014/8/25.
 */
public interface Transaction {

    <T> T execute();
}
