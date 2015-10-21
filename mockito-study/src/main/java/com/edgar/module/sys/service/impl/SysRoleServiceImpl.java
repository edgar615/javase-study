package com.edgar.module.sys.service.impl;

import com.edgar.core.repository.BaseDao;
import com.edgar.core.repository.IDUtils;
import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.validator.ValidatorBus;
import com.edgar.module.sys.repository.domain.*;
import com.edgar.module.sys.service.SysRoleService;
import com.edgar.module.sys.validator.SysRoleUpdateValidator;
import com.edgar.module.sys.validator.SysRoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色的业务逻辑实现
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private BaseDao<Integer, SysRoleRoute> sysRoleRouteDao;

    @Autowired
    private BaseDao<Integer, SysRoleMenu> sysRoleMenuDao;

    @Autowired
    private BaseDao<Integer, SysRoleRes> sysRoleResDao;

    @Autowired
    private BaseDao<Integer, SysRole> sysRoleDao;

    @Autowired
    private BaseDao<Integer, SysUserRole> sysUserRoleDao;

    @Autowired
    private ValidatorBus validatorBus;

    @Override
    @Transactional
    public void save(SysRole sysRole) {
        validatorBus.validator(sysRole, SysRoleValidator.class);
        sysRole.setIsRoot(false);
        sysRole.setRoleId(IDUtils.getNextId());
        sysRoleDao.insert(sysRole);
    }

    @Override
    @Transactional
    public void update(SysRole sysRole) {
        validatorBus.validator(sysRole, SysRoleUpdateValidator.class);
        sysRoleDao.update(sysRole);
    }

    @Override
    public SysRole get(int roleId) {
        return sysRoleDao.get(roleId);
    }

    @Override
    public Pagination<SysRole> pagination(QueryExample example, int page, int pageSize) {
        example.equalsTo("isRoot", 0);
        return sysRoleDao.pagination(example, page, pageSize);
    }

    @Override
    public List<SysRole> query(QueryExample example) {
        example.equalsTo("isRoot", 0);
        return sysRoleDao.query(example);
    }

    @Override
    @Transactional
    public void deleteWithLock(int roleId, long updatedTime) {
        sysRoleDao.deleteByPkAndVersion(roleId, updatedTime);
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("roleId", roleId);
        sysUserRoleDao.delete(example);
        sysRoleRouteDao.delete(example);
        sysRoleMenuDao.delete(example);
        sysRoleResDao.delete(example);
    }

    public void setSysRoleRouteDao(BaseDao<Integer, SysRoleRoute> sysRoleRouteDao) {
        this.sysRoleRouteDao = sysRoleRouteDao;
    }

    public void setSysRoleMenuDao(BaseDao<Integer, SysRoleMenu> sysRoleMenuDao) {
        this.sysRoleMenuDao = sysRoleMenuDao;
    }

    public void setSysRoleResDao(BaseDao<Integer, SysRoleRes> sysRoleResDao) {
        this.sysRoleResDao = sysRoleResDao;
    }

    public void setSysRoleDao(BaseDao<Integer, SysRole> sysRoleDao) {
        this.sysRoleDao = sysRoleDao;
    }

    public void setSysUserRoleDao(BaseDao<Integer, SysUserRole> sysUserRoleDao) {
        this.sysUserRoleDao = sysUserRoleDao;
    }

    public void setValidatorBus(ValidatorBus validatorBus) {
        this.validatorBus = validatorBus;
    }
}
