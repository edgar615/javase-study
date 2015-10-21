package com.edgar.core.repository;

import com.edgar.module.sys.repository.domain.TestTable;
import com.edgar.module.sys.repository.querydsl.QTestTable;
import com.mysema.query.sql.RelationalPathBase;
import org.springframework.stereotype.Repository;

@Repository
public class TestTableDao extends AbstractDaoTemplate<String, TestTable> {

    @Override
    public RelationalPathBase<?> getPathBase() {
        return QTestTable.testTable;
    }

}
