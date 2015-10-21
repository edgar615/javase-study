package com.edgar.core.repository;

import com.edgar.core.util.ServiceLookup;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-9
 * Time: 上午9:32
 * To change this template use File | Settings | File Templates.
 */
public abstract class DataSourceFactory {

    public static final DataSource createDataSource(String dataSourceKey) {
        DataSource dataSource = ServiceLookup.getBean(DataSource.class);
        return dataSource;
    }
}
