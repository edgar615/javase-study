package com.edgar.module.sys.dao;

import com.edgar.core.repository.AbstractDaoTemplate;
import com.edgar.module.sys.repository.domain.SysMenuRoute;
import com.edgar.module.sys.repository.querydsl.QSysMenuRoute;
import com.mysema.query.sql.RelationalPathBase;
import org.springframework.stereotype.Repository;

/**
 * 菜单路由关联的DAO
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Repository
public class SysMenuRouteDao extends AbstractDaoTemplate<Integer, SysMenuRoute> {

    @Override
    public RelationalPathBase<?> getPathBase() {
        return QSysMenuRoute.sysMenuRoute;
    }
}
