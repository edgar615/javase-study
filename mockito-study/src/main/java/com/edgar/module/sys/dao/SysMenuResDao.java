package com.edgar.module.sys.dao;

import com.edgar.core.repository.AbstractDaoTemplate;
import com.edgar.module.sys.repository.domain.SysMenuRes;
import com.edgar.module.sys.repository.querydsl.QSysMenuRes;
import com.mysema.query.sql.RelationalPathBase;
import org.springframework.stereotype.Repository;

/**
 * 菜单资源的DAO
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Repository
public class SysMenuResDao extends
        AbstractDaoTemplate<Integer, SysMenuRes> {

    @Override
    public RelationalPathBase<?> getPathBase() {
        return QSysMenuRes.sysMenuRes;
    }


}
