package com.edgar.core.repository.transaction;

import com.edgar.core.repository.QueryExample;
import com.edgar.core.repository.QueryExampleHelper;
import com.mysema.query.sql.SQLBindings;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Administrator on 2014/8/25.
 */
public class CountTransaction extends TransactionTemplate {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CountTransaction.class);

    private final QueryExample example;

    protected CountTransaction(TransactionConfig config, QueryExample example) {
        super(config);
        this.example = example;
    }

    public Long execute() {
        final SQLQuery sqlQuery = new SQLQuery(configuration);
        sqlQuery.from(pathBase);
        addSpec(sqlQuery);
        SQLBindings sqlBindings = sqlQuery.getSQL(pathBase.getPrimaryKey()
                .getLocalColumns().get(0));
        StringBuilder sql = new StringBuilder("select count(*) from ("
                + sqlBindings.getSQL() + ") x");
        LOGGER.debug("SQL: {} \nparams: {}", sql, sqlBindings.getBindings());
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForObject(sql.toString(), sqlBindings
                .getBindings().toArray(), Long.class);
    }

    private void addSpec(final SQLQuery sqlQuery) {
        for (BooleanExpression expression : QueryExampleHelper.getExpressions(pathBase, example)) {
            sqlQuery.where(expression);
        }
        addLimit(example, sqlQuery);
        addOffset(example, sqlQuery);
        for (OrderSpecifier<?> spec : QueryExampleHelper.getOrderSpecs(pathBase, example)) {
            sqlQuery.orderBy(spec);
        }
    }

    /**
     * 设置limit值，如果limit小于0，则不设置此值
     *
     * @param example  查询条件
     * @param sqlQuery QueryDSL的查询核心类SQLQuery
     */
    protected void addLimit(QueryExample example, SQLQuery sqlQuery) {
        if (example.getLimit() > 0) {
            sqlQuery.limit(example.getLimit());
        }
    }

    /**
     * 设置offset，如果offset小于0，则不设置此值
     *
     * @param example  查询条件
     * @param sqlQuery QueryDSL的查询核心类SQLQuery
     */
    protected void addOffset(QueryExample example, SQLQuery sqlQuery) {
        if (example.getOffset() > 0) {
            sqlQuery.offset(example.getOffset());
        }
    }
}
