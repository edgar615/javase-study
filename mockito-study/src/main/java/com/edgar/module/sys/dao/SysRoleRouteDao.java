package com.edgar.module.sys.dao;

import com.edgar.core.repository.AbstractDaoTemplate;
import com.edgar.module.sys.repository.domain.SysRoleRoute;
import com.edgar.module.sys.repository.querydsl.QSysRoleRoute;
import com.mysema.query.sql.RelationalPathBase;
import org.springframework.stereotype.Repository;

/**
 * 角色路由DAO
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Repository
public class SysRoleRouteDao extends AbstractDaoTemplate<Integer, SysRoleRoute> {

    @Override
    public RelationalPathBase<?> getPathBase() {
        return QSysRoleRoute.sysRoleRoute;
    }

}
