package com.edgar.core.repository.transaction;

import com.mysema.query.sql.SQLBindings;
import com.mysema.query.sql.dml.DefaultMapper;
import com.mysema.query.sql.dml.SQLInsertClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Administrator on 2014/8/31.
 */
public class InsertWithKeyTransaction<T, K> extends TransactionTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsertWithKeyTransaction.class);

    private final T domain;

    private final boolean withNullBindings;

    private final Class<K> keyClass;

    protected InsertWithKeyTransaction(TransactionConfig config, T domain, boolean withNullBindings, Class<K> keyClass) {
        super(config);
        this.domain = domain;
        this.withNullBindings = withNullBindings;
        this.keyClass = keyClass;
    }

    @Override
    public K execute() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.execute(new ConnectionCallback<K>() {
            @Override
            public K doInConnection(Connection connection) throws SQLException, DataAccessException {
                final SQLInsertClause insertClause = new SQLInsertClause(connection, configuration, pathBase);
                populate(insertClause);
                log(insertClause);
                return insertClause.executeWithKey(keyClass);
            }
        });
    }

    private void populate(SQLInsertClause insertClause) {
        if (withNullBindings) {
            insertClause.populate(domain, DefaultMapper.WITH_NULL_BINDINGS);
        } else {
            insertClause.populate(domain);
        }
    }

    private void log(SQLInsertClause insertClause) {
        for (SQLBindings sqlBindings : insertClause.getSQL()) {
            LOGGER.debug("SQL: {} \nparams: {}", sqlBindings.getSQL(), sqlBindings.getBindings());
        }
    }

}
