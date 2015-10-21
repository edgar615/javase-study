package com.edgar.module.sys.dao;

import com.edgar.core.repository.AbstractDaoTemplate;
import com.edgar.module.sys.repository.domain.SysRoute;
import com.edgar.module.sys.repository.querydsl.QSysRoute;
import com.mysema.query.sql.RelationalPathBase;
import org.springframework.stereotype.Repository;

/**
 * 系统路由的DAO
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Repository
public class SysRouteDao extends AbstractDaoTemplate<Integer, SysRoute> {

    @Override
    public RelationalPathBase<?> getPathBase() {
        return QSysRoute.sysRoute;
    }

}
