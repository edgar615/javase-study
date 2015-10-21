package com.edgar.core.repository.transaction;

import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.rits.cloning.Cloner;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * Created by Administrator on 2014/8/25.
 */
public class PageTransaction<T> extends TransactionTemplate {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PageTransaction.class);

    private final RowMapper<T> rowMapper;

    private int page;

    private final int pageSize;

    private final QueryExample example;

    protected PageTransaction(TransactionConfig config, QueryExample example, int page, int pageSize, RowMapper<T> rowMapper) {
        super(config);
        this.example = example;
        this.page = page;
        this.pageSize = pageSize;
        this.rowMapper = rowMapper;
    }

    public Pagination<T> execute() {
        LOGGER.debug("pagination query {},page:{},pageSize:{}", pathBase
                .getTableName(), page, pageSize);
        Validate.notNull(example);
        Validate.isTrue(page > 0, "page must > 1");
        Validate.isTrue(pageSize > 0, "pageSize must > 0");
        if (example.getMaxNumOfRecords() > 0) {
            Validate.isTrue(page * pageSize <= example.getMaxNumOfRecords(),
                    "page * pageSize cannot >：" + example.getMaxNumOfRecords());
        }

        int offset = calCountOffset();

        Long totalRecords = count();
        calQueryOffset(offset, totalRecords);
        return Pagination.newInstance(page, pageSize, totalRecords, query());
    }

    private List<T> query() {
        Transaction query = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        return query.execute();
    }

    private void calQueryOffset(int offset, Long totalRecords) {
        if (totalRecords <= offset) {
            page = (int) (totalRecords / pageSize);
            if (page == 0) {
                page = 1;
            }
            example.offset((page - 1) * pageSize);
        }
    }

    private int calCountOffset() {
        example.limit(pageSize);
        int offset = (page - 1) * pageSize;
        example.offset(offset);
        return offset;
    }

    private Long count() {
        final QueryExample COUNT_EXAMPLE = cloneExample(example);
        if (example.getMaxNumOfRecords() > 0) {
            COUNT_EXAMPLE.limit(example.getMaxNumOfRecords());
        } else {
            COUNT_EXAMPLE.limit(0);
        }
        COUNT_EXAMPLE.offset(0);
        Transaction transaction = TransactionFactory.createCountTransaction(config, COUNT_EXAMPLE);
        return transaction.execute();
    }

    /**
     * 克隆查询条件
     *
     * @param example 查询条件
     * @return 克隆后的查询条件
     */
    private QueryExample cloneExample(final QueryExample example) {
        Cloner cloner = new Cloner();
        return cloner.deepClone(example);
    }

}
