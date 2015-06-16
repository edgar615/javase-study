package com.edgar.core.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/16.
 */
@Repository
public class PageMapper {

    private static final String OFFSET = "offset";
    private static final String LIMIT = "limit";
    private static final int MAX_RECORDS = 10000;

    @Autowired
    private SqlSession session;

    public <T> Pagination<T> fetchPage(Map<String, Object> params, final int page, final int pageSize, String countStmt, String selectStmt) {
        params.put(OFFSET, 0);
        params.put(LIMIT, MAX_RECORDS);
        //查询总数
        final int totalRecords = session.selectOne(countStmt, Collections.unmodifiableMap(params));
        //计算页面
        int pageCount = totalRecords / pageSize;
        if (totalRecords > pageSize * pageCount) {
            pageCount++;
        }
        // TODO 计算是否是最后一页
        int offset = (page - 1) * pageSize;
        params.put(OFFSET, offset);
        params.put(LIMIT, pageSize);
        List<T> records = session.selectList(selectStmt, Collections.unmodifiableMap(params));
        return Pagination.newInstance(page, pageSize, totalRecords, records);
    }
}
