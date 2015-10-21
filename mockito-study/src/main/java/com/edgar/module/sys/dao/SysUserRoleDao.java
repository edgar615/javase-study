package com.edgar.module.sys.dao;

import com.edgar.core.repository.AbstractDaoTemplate;
import com.edgar.module.sys.repository.domain.SysUserRole;
import com.edgar.module.sys.repository.querydsl.QSysUserRole;
import com.mysema.query.sql.RelationalPathBase;
import org.springframework.stereotype.Repository;

/**
 * 用户角色的DAO
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Repository
public class SysUserRoleDao extends AbstractDaoTemplate<Integer, SysUserRole> {

    @Override
    public RelationalPathBase<?> getPathBase() {
        return QSysUserRole.sysUserRole;
    }

}
