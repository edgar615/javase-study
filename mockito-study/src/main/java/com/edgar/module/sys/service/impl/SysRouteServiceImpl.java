package com.edgar.module.sys.service.impl;

import com.edgar.core.repository.BaseDao;
import com.edgar.core.repository.IDUtils;
import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.validator.ValidatorBus;
import com.edgar.module.sys.repository.domain.SysMenuRoute;
import com.edgar.module.sys.repository.domain.SysRoleRoute;
import com.edgar.module.sys.repository.domain.SysRoute;
import com.edgar.module.sys.service.SysRouteService;
import com.edgar.module.sys.validator.SysRouteUpdateValidator;
import com.edgar.module.sys.validator.SysRouteValidator;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 路由的业务逻辑实现类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Service
public class SysRouteServiceImpl implements SysRouteService {
    @Autowired
    private BaseDao<Integer, SysRoute> sysRouteDao;

    @Autowired
    private BaseDao<Integer, SysRoleRoute> sysRoleRouteDao;

    @Autowired
    private BaseDao<Integer, SysMenuRoute> sysMenuRouteDao;

    @Autowired
    private ValidatorBus validatorBus;

    @Override
    public List<SysRoute> findAll() {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("isRoot", 0);
        return sysRouteDao.query(example);
    }

    @Override
    public List<SysRoute> findAllWithRoot() {
        QueryExample example = QueryExample.newInstance();
        return sysRouteDao.query(example);
    }


    @Override
    @Transactional
    public void save(SysRoute sysRoute) {
        validatorBus.validator(sysRoute, SysRouteValidator.class);
        sysRoute.setIsRoot(false);
        sysRoute.setRouteId(IDUtils.getNextId());
        sysRouteDao.insert(sysRoute);
    }

    @Override
    @Transactional
    public void update(SysRoute sysRoute) {
        validatorBus.validator(sysRoute, SysRouteUpdateValidator.class);
        Validate.notNull(sysRoute);
        sysRouteDao.update(sysRoute);
    }

    @Override
    public SysRoute get(int routeId) {
        return sysRouteDao.get(routeId);
    }

    @Override
    public Pagination<SysRoute> pagination(QueryExample example, int page,
                                           int pageSize) {
        example.equalsTo("isRoot", 0);
        return sysRouteDao.pagination(example, page, pageSize);
    }

    @Override
    @Transactional
    public void deleteWithLock(int routeId, long updatedTime) {
        sysRouteDao.deleteByPkAndVersion(routeId, updatedTime);
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("routeId", routeId);
        sysRoleRouteDao.delete(example);
        sysMenuRouteDao.delete(example);
    }

    public void setSysRouteDao(BaseDao<Integer, SysRoute> sysRouteDao) {
        this.sysRouteDao = sysRouteDao;
    }

    public void setSysRoleRouteDao(BaseDao<Integer, SysRoleRoute> sysRoleRouteDao) {
        this.sysRoleRouteDao = sysRoleRouteDao;
    }

    public void setSysMenuRouteDao(BaseDao<Integer, SysMenuRoute> sysMenuRouteDao) {
        this.sysMenuRouteDao = sysMenuRouteDao;
    }

    public void setValidatorBus(ValidatorBus validatorBus) {
        this.validatorBus = validatorBus;
    }
}
