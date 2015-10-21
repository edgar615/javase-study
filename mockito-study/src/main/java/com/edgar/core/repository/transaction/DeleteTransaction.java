package com.edgar.core.repository.transaction;

import com.edgar.core.repository.QueryExample;
import com.edgar.core.repository.QueryExampleHelper;
import com.mysema.query.sql.SQLBindings;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.types.expr.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Administrator on 2014/8/29.
 */
public class DeleteTransaction extends TransactionTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTransaction.class);

    private final QueryExample example;

    protected DeleteTransaction(TransactionConfig config, QueryExample example) {
        super(config);
        this.example = example;
    }

    @Override
    public Long execute() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.execute(new ConnectionCallback<Long>() {
            @Override
            public Long doInConnection(Connection connection) throws SQLException, DataAccessException {
                final SQLDeleteClause deleteClause = new SQLDeleteClause(connection, configuration, pathBase);
                for (BooleanExpression expression : QueryExampleHelper.getExpressions(pathBase, example)) {
                    deleteClause.where(expression);
                }
                for (SQLBindings sqlBindings : deleteClause.getSQL()) {
                    LOGGER.debug("SQL: {} \nparams: {}", sqlBindings.getSQL(), sqlBindings.getBindings());
                }
                return deleteClause.execute();
            }
        });
    }

}
