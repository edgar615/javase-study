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
import java.util.List;

/**
 * Created by Administrator on 2014/8/31.
 */
public class BatchInsertTransaction<T> extends TransactionTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchInsertTransaction.class);

    private final List<T> domains;

    private final boolean withNullBindings;

    protected BatchInsertTransaction(TransactionConfig config, List<T> domains, boolean withNullBindings) {
        super(config);
        this.domains = domains;
        this.withNullBindings = withNullBindings;
    }

    @Override
    public Long execute() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.execute(new ConnectionCallback<Long>() {
            @Override
            public Long doInConnection(Connection connection) throws SQLException, DataAccessException {
                final SQLInsertClause insertClause = new SQLInsertClause(connection, configuration, pathBase);
                addBatch(insertClause);
                log(insertClause);
                return insertClause.execute();
            }
        });
    }

    private void addBatch(SQLInsertClause insertClause) {
        for (T domain : domains) {
            if (withNullBindings) {
                insertClause.populate(domain, DefaultMapper.WITH_NULL_BINDINGS).addBatch();
            } else {
                insertClause.populate(domain).addBatch();
            }
        }
    }

    private void log(SQLInsertClause insertClause) {
        for (SQLBindings sqlBindings : insertClause.getSQL()) {
            LOGGER.debug("SQL: {} \nparams: {}", sqlBindings.getSQL(), sqlBindings.getBindings());
        }
    }

}
