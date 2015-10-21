package com.edgar.module.sys.dao;

import com.edgar.core.repository.AbstractDaoTemplate;
import com.edgar.module.sys.repository.domain.SysRole;
import com.edgar.module.sys.repository.querydsl.QSysRole;
import com.mysema.query.sql.RelationalPathBase;
import org.springframework.stereotype.Repository;

/**
 * 系统角色的DAO
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Repository
public class SysRoleDao extends AbstractDaoTemplate<Integer, SysRole> {

    @Override
    public RelationalPathBase<?> getPathBase() {
        return QSysRole.sysRole;
    }

}
