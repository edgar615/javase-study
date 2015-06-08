package com.edgar.core.repository;

import com.edgar.core.jdbc.Pagination;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PaginationHelper {

    private static final String OFFSET = "offset";
    private static final String LIMIT = "limit";
    private static final int MAX_RECORDS = 10000;
    /**
     * 直接计算总数
     * @param mapper
     * @param params
     * @param page
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> Pagination fetchPage(BaseMapper mapper, Map<String, Object> params, final int page, final int pageSize) {
        params.put(OFFSET, 0);
        params.put(LIMIT, MAX_RECORDS);
        //查询总数
        final int totalRecords = mapper.count(Collections.unmodifiableMap(params));
        //计算页面
        int pageCount = totalRecords / pageSize;
        if (totalRecords > pageSize * pageCount) {
            pageCount++;
        }
        // TODO 计算是否是最后一页
        int offset = (page - 1) * pageSize;
        params.put(OFFSET, offset);
        params.put(LIMIT, pageSize);
        List<T> records = mapper.query(Collections.unmodifiableMap(params));
        return Pagination.newInstance(page, pageSize, totalRecords, records);
    }

//    private Long count() {
//        final QueryExample COUNT_EXAMPLE = cloneExample(example);
//        if (example.getMaxNumOfRecords() > 0) {
//            COUNT_EXAMPLE.limit(example.getMaxNumOfRecords());
//        } else {
//            COUNT_EXAMPLE.limit(0);
//        }
//        COUNT_EXAMPLE.offset(0);
//        Transaction transaction = TransactionFactory.createCountTransaction(config, COUNT_EXAMPLE);
//        return transaction.execute();
//    }
}  