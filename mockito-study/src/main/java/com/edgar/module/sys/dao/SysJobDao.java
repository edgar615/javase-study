package com.edgar.module.sys.dao;

import com.edgar.core.repository.AbstractDaoTemplate;
import com.edgar.module.sys.repository.domain.SysJob;
import com.edgar.module.sys.repository.querydsl.QSysJob;
import com.mysema.query.sql.RelationalPathBase;
import org.springframework.stereotype.Repository;

@Repository
public class SysJobDao extends AbstractDaoTemplate<Integer, SysJob> {

    @Override
    public RelationalPathBase<?> getPathBase() {
        return QSysJob.sysJob;
    }

}
