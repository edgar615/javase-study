package com.edgar.core.repository.transaction;

import com.mysema.query.sql.Configuration;
import com.mysema.query.sql.RelationalPathBase;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-3
 * Time: 上午11:34
 * To change this template use File | Settings | File Templates.
 */
public class TransactionConfig {
    private final Configuration configuration;

    private final RelationalPathBase<?> pathBase;

    private final DataSource dataSource;

    public TransactionConfig(DataSource dataSource, Configuration configuration, RelationalPathBase<?> pathBase) {
        this.dataSource = dataSource;
        this.configuration = configuration;
        this.pathBase = pathBase;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public RelationalPathBase<?> getPathBase() {
        return pathBase;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
