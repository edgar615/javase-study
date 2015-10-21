package com.edgar.module.sys.dao;

import com.edgar.core.repository.AbstractDaoTemplate;
import com.edgar.module.sys.repository.domain.SysMenu;
import com.edgar.module.sys.repository.querydsl.QSysMenu;
import com.mysema.query.sql.RelationalPathBase;
import org.springframework.stereotype.Repository;

/**
 * 系统菜单的DAO
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Repository
public class SysMenuDao extends AbstractDaoTemplate<Integer, SysMenu> {

    @Override
    public RelationalPathBase<?> getPathBase() {
        return QSysMenu.sysMenu;
    }

}
