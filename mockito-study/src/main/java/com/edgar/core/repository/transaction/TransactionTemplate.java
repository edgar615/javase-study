package com.edgar.core.repository.transaction;

import com.mysema.query.sql.Configuration;
import com.mysema.query.sql.RelationalPathBase;

import javax.sql.DataSource;

/**
 * Created by Administrator on 2014/8/29.
 */
public abstract class TransactionTemplate implements Transaction {

    protected final Configuration configuration;

    protected final RelationalPathBase<?> pathBase;

    protected final DataSource dataSource;

    protected final TransactionConfig config;

    protected TransactionTemplate(TransactionConfig config) {
        this.config = config;
        this.configuration = config.getConfiguration();
        this.pathBase = config.getPathBase();
        this.dataSource = config.getDataSource();
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
