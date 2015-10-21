package com.edgar.core.repository.transaction;

import com.edgar.core.repository.Constants;
import com.edgar.core.repository.QueryExample;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-3
 * Time: 上午11:26
 * To change this template use File | Settings | File Templates.
 */
public class TransactionFactory {

    public static Transaction createCountTransaction(TransactionConfig config, QueryExample example) {
        return new CountTransaction(config, example);
    }

    public static Transaction createDeleteTransaction(TransactionConfig config, QueryExample example) {
        return new DeleteTransaction(config, example);
    }

    public static <T> Transaction createUpdateTransaction(TransactionConfig config, T domain, boolean withNullBindings, QueryExample example, String... ignore) {
        return new UpdateTransaction(config, domain, withNullBindings, example, ignore);
    }

    public static <T> Transaction createDefaultUpdateTransaction(TransactionConfig config, T domain, QueryExample example) {
        return new UpdateTransaction(config, domain, false, example, Constants.CREATED_TIME, Constants.UPDATED_TIME);
    }

    public static <T> Transaction createQueryTransaction(TransactionConfig config, QueryExample example, RowMapper<T> rowMapper) {
        return new QueryTransaction(config, example, rowMapper);
    }

    public static <T> Transaction createPageTransaction(TransactionConfig config, QueryExample example, int page, int pageSize, RowMapper<T> rowMapper) {
        return new PageTransaction(config, example, page, pageSize, rowMapper);
    }

    public static <T> Transaction createInsertTransaction(TransactionConfig config, T domain, boolean withNullBindings) {
        return new InsertTransaction(config, domain, withNullBindings);
    }

    public static <T> Transaction createDefaultInsertTransaction(TransactionConfig config, T domain) {
        return createInsertTransaction(config, domain, false);
    }

    public static <T, K> Transaction createInsertWithKeyTransaction(TransactionConfig config, T domain, boolean withNullBindings, Class<K> keyClass) {
        return new InsertWithKeyTransaction(config, domain, withNullBindings, keyClass);
    }

    public static <T, K> Transaction createDefaultInsertWithKeyTransaction(TransactionConfig config, T domain, Class<K> keyClass) {
        return createInsertWithKeyTransaction(config, domain, false, keyClass);
    }

    public static <T> Transaction createBatchInsertTransaction(TransactionConfig config, List<T> domains, boolean withNullBindings) {
        return new BatchInsertTransaction(config, domains, withNullBindings);
    }

    public static <T> Transaction createDefaultBatchInsertTransaction(TransactionConfig config, List<T> domains) {
        return createBatchInsertTransaction(config, domains, false);
    }
}
