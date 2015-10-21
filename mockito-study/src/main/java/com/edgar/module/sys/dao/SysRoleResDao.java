package com.edgar.module.sys.dao;

import com.edgar.core.repository.AbstractDaoTemplate;
import com.edgar.module.sys.repository.domain.SysRoleRes;
import com.edgar.module.sys.repository.querydsl.QSysRoleRes;
import com.mysema.query.sql.RelationalPathBase;
import org.springframework.stereotype.Repository;

/**
 * 角色资源的DAO
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Repository
public class SysRoleResDao extends AbstractDaoTemplate<Integer, SysRoleRes> {

    @Override
    public RelationalPathBase<?> getPathBase() {
        return QSysRoleRes.sysRoleRes;
    }

}
