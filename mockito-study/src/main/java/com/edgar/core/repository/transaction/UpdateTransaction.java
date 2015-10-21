package com.edgar.core.repository.transaction;

import com.edgar.core.repository.QueryExample;
import com.edgar.core.repository.QueryExampleHelper;
import com.mysema.query.sql.SQLBindings;
import com.mysema.query.sql.dml.DefaultMapper;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * 即使修改了实体的主键，SQLUpdateClause.populate(domain)方法也不会更新主键，如果实体的属性值为null，则不会更新。
 * withNullBindings：false会忽略domain中的null值
 */
public class UpdateTransaction<T> extends TransactionTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTransaction.class);

    private final T domain;

    private final boolean withNullBindings;

    private final QueryExample example;

    private final Set<String> ignoreColumns = new HashSet<String>();

    protected UpdateTransaction(TransactionConfig config, T domain, boolean withNullBindings, QueryExample example, String... ignore) {
        super(config);
        this.domain = domain;
        this.withNullBindings = withNullBindings;
        this.example = example;
        Collections.addAll(ignoreColumns, ignore);
    }

    public Long execute() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.execute(new ConnectionCallback<Long>() {
            @Override
            public Long doInConnection(Connection connection) throws SQLException, DataAccessException {
                final SQLUpdateClause updateClause = new SQLUpdateClause(connection, configuration, pathBase);
                addSpec(updateClause);
                populate(updateClause);
                log(updateClause);
                return updateClause.execute();
            }
        });
    }

    private void log(SQLUpdateClause updateClause) {
        for (SQLBindings sqlBindings : updateClause.getSQL()) {
            LOGGER.debug("SQL: {} \nparams: {}", getPathBase()
                    .getTableName(), sqlBindings.getSQL(), sqlBindings.getBindings());
        }
    }

    /**
     * 因为需要忽略createdTime、updatedTime，所以并没有直接使用 updateClause.populate(domain)或updateClause.populate(domain, DefaultMapper.WITH_NULL_BINDINGS)来实现set
     *
     * @param updateClause
     * @param obj
     * @param mapper
     */
    public void populate(SQLUpdateClause updateClause, T obj, DefaultMapper mapper) {
        Collection<? extends Path<?>> primaryKeyColumns = pathBase.getPrimaryKey() != null
                ? pathBase.getPrimaryKey().getLocalColumns()
                : Collections.<Path<?>>emptyList();
        Map<Path<?>, Object> values = mapper.createMap(pathBase, obj);
        for (Map.Entry<Path<?>, Object> entry : values.entrySet()) {
            if (!primaryKeyColumns.contains(entry.getKey()) && !ignoreColumns.contains(entry.getKey().getMetadata().getName())) {
                updateClause.set((Path) entry.getKey(), entry.getValue());
            }
        }
    }

    private void populate(SQLUpdateClause updateClause) {
        //不会更新主键
        if (withNullBindings) {
            populate(updateClause, domain, DefaultMapper.WITH_NULL_BINDINGS);
        } else {
            populate(updateClause, domain, DefaultMapper.DEFAULT);
        }
    }

    private void addSpec(final SQLUpdateClause updateClause) {
        for (BooleanExpression expression : QueryExampleHelper.getExpressions(pathBase, example)) {
            updateClause.where(expression);
        }
    }

}
