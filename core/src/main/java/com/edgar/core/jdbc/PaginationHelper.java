package com.edgar.core.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class PaginationHelper<T> {
    public Pagination<T> fetchPage(final JdbcTemplate jdbcTemplate,
                                   final String sqlCountRows, final String sqlFetchRows,
                                   final Object args[], final int page, final int pageSize,
                                   final RowMapper<T> rowMapper) {
        // 查询总数
        final int totalRecords = jdbcTemplate.queryForObject(sqlCountRows, args, Integer.class);
        //计算页面
        int pageCount = totalRecords / pageSize;
        if (totalRecords > pageSize * pageCount) {
            pageCount++;
        }
        // TODO 计算是否是最后一页
        int offset = (page - 1) * pageSize;

        List<T> records = jdbcTemplate.query(sqlFetchRows, args, rowMapper);
        return Pagination.newInstance(page, pageSize, totalRecords, records);
//        // create the page object
//        final CurrentPage<T> page = new CurrentPage<T>();
//        page.setPageNumber(pageNo);
//        page.setPagesAvailable(pageCount);
//        // fetch a single page of results
//        final int startRow = (pageNo - 1) * pageSize;
//        jt.query(sqlFetchRows, args, new ResultSetExtractor() {
//            public Object extractData(ResultSet rs) throws SQLException,
//                    DataAccessException {
//                final List pageItems = page.getPageItems();
//                int currentRow = 0;
//                while (rs.next() && currentRow < startRow + pageSize) {
//                    if (currentRow >= startRow) {
//                        pageItems.add(rowMapper.mapRow(rs, currentRow));
//                    }
//                    currentRow++;
//                }
//                return page;
//            }
//        });
//        return page;
    }

//        private void calQueryOffset(int offset, Long totalRecords) {
//        if (totalRecords <= offset) {
//            page = (int) (totalRecords / pageSize);
//            if (page == 0) {
//                page = 1;
//            }
//            example.offset((page - 1) * pageSize);
//        }
//        }
//
//
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