package com.edgar.module.sys.dao;

import com.edgar.core.repository.AbstractDaoTemplate;
import com.edgar.module.sys.repository.domain.SysCompany;
import com.edgar.module.sys.repository.querydsl.QSysCompany;
import com.mysema.query.sql.RelationalPathBase;
import org.springframework.stereotype.Repository;

@Repository
public class SysCompanyDao extends
        AbstractDaoTemplate<Integer, SysCompany> {

    @Override
    public RelationalPathBase<?> getPathBase() {
        return QSysCompany.sysCompany;
    }

}
