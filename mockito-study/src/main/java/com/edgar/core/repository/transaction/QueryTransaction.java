package com.edgar.core.repository.transaction;

import com.edgar.core.repository.QueryExample;
import com.edgar.core.repository.QueryExampleHelper;
import com.mysema.query.sql.SQLBindings;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.BooleanExpression;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/8/25.
 */
public class QueryTransaction<T> extends TransactionTemplate {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(QueryTransaction.class);

    private final QueryExample example;

    private final RowMapper<T> rowMapper;

    private final List<Path<?>> returnPaths = new ArrayList<Path<?>>();

    protected QueryTransaction(TransactionConfig config, QueryExample example, RowMapper<T> rowMapper) {
        super(config);
        this.example = example;
        this.rowMapper = rowMapper;
    }

    public List<T> execute() {
        Validate.notNull(example);
        final SQLQuery sqlQuery = new SQLQuery(configuration);
        sqlQuery.from(pathBase);
        addSpec(sqlQuery);
        returnPaths.addAll(QueryExampleHelper.getReturnPath(pathBase, example));
        Path<?>[] pathArray = new Path<?>[returnPaths.size()];
        SQLBindings sqlBindings = sqlQuery.getSQL(returnPaths.toArray(pathArray));
        String sql = sqlBindings.getSQL();
        List<Object> args = sqlBindings.getBindings();
        LOGGER.debug("SQL: {} \nparams: {}", sql, args);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query(sql, args.toArray(), rowMapper);
    }

    private void addSpec(SQLQuery sqlQuery) {
        for (BooleanExpression expression : QueryExampleHelper.getExpressions(pathBase, example)) {
            sqlQuery.where(expression);
        }
        if (example.isDistinct()) {
            sqlQuery.distinct();
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
