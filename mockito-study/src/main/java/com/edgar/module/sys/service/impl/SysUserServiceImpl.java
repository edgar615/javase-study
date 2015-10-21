package com.edgar.module.sys.service.impl;

import com.edgar.core.repository.BaseDao;
import com.edgar.core.repository.IDUtils;
import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.util.Constants;
import com.edgar.core.util.ExceptionFactory;
import com.edgar.core.validator.ValidatorBus;
import com.edgar.module.sys.repository.domain.SysRole;
import com.edgar.module.sys.repository.domain.SysUser;
import com.edgar.module.sys.repository.domain.SysUserProfile;
import com.edgar.module.sys.repository.domain.SysUserRole;
import com.edgar.module.sys.service.PasswordService;
import com.edgar.module.sys.service.SysUserService;
import com.edgar.module.sys.validator.PasswordValidator;
import com.edgar.module.sys.validator.SysUserUpdateValidator;
import com.edgar.module.sys.validator.SysUserValidator;
import com.edgar.module.sys.vo.ChangePasswordVo;
import com.edgar.module.sys.vo.SysUserRoleVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户的业务逻辑实现类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private BaseDao<Integer, SysUser> sysUserDao;

    @Autowired
    private BaseDao<Integer, SysRole> sysRoleDao;

    @Autowired
    private BaseDao<Integer, SysUserRole> sysUserRoleDao;

    @Autowired
    private BaseDao<Integer, SysUserProfile> sysUserProfileDao;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private ValidatorBus validatorBus;

    @Override
    @Transactional
    public void saveAdminUser(SysUser sysUser) {
        SysUserRoleVo sysUserRoleVo = new SysUserRoleVo();
        BeanUtils.copyProperties(sysUser, sysUserRoleVo);
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("roleCode", "ROLE_CODE_ADMIN");
        example.addField("roleId");
        List<Integer> roleIds = sysRoleDao.querySingleColumn(example,
                Integer.class);
        sysUserRoleVo.setRoleIds(StringUtils.join(roleIds, ","));
        save(sysUserRoleVo);
    }

    @Override
    @Transactional
    public void save(SysUserRoleVo sysUserRoleVo) {
        validatorBus.validator(sysUserRoleVo, SysUserValidator.class);
        sysUserRoleVo.setUserId(IDUtils.getNextId());
        sysUserRoleVo.setIsRoot(false);
        passwordService.encryptPassword(sysUserRoleVo);
        sysUserDao.insert(sysUserRoleVo);
        insertSysUserRoles(sysUserRoleVo);
        saveDefaultProfile(sysUserRoleVo.getUserId());
    }

    @Override
    public SysUser get(int userId) {
        SysUser sysUser = sysUserDao.get(userId);
        sysUser.setPassword(null);
        return sysUser;
    }

    @Override
    public Pagination<SysUser> pagination(QueryExample example, int page,
                                          int pageSize) {
        example.equalsTo("isRoot", 0);
        return sysUserDao.pagination(example, page, pageSize);
    }

    @Override
    @Transactional
    public void update(SysUserRoleVo sysUser) {
        validatorBus.validator(sysUser, SysUserUpdateValidator.class);
        if (StringUtils.isNotBlank(sysUser.getPassword())) {
            passwordService.encryptPassword(sysUser);
        }
        sysUserDao.update(sysUser);
        deleteRoleByUser(sysUser.getUserId());
        insertSysUserRoles(sysUser);
    }

    @Override
    @Transactional
    public void deleteWithLock(int userId, long updatedTime) {
        deleteRoleByUser(userId);
        deleteProfile(userId);
        sysUserDao.deleteByPkAndVersion(userId, updatedTime);
    }

    @Override
    public boolean checkUsername(String username) {
        List<SysUser> sysUsers = queryByUsername(username);
        return sysUsers.isEmpty();
    }

    @Override
    public List<SysUser> queryByUsername(String username) {
        Validate.notNull(username);
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("username", username);
        return sysUserDao.query(example);
    }

    @Override
    public List<SysUserRole> getRoles(int userId) {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("userId", userId);
        return sysUserRoleDao.query(example);
    }

    /**
     * 根据用户删除角色
     *
     * @param userId 用户ID
     */
    @Transactional
    private void deleteRoleByUser(int userId) {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("userId", userId);
        sysUserRoleDao.delete(example);
    }

    /**
     * 新增用户角色
     *
     * @param sysUser 用户角色
     */
    @Transactional
    private void insertSysUserRoles(SysUserRoleVo sysUser) {
        if (StringUtils.isNotBlank(sysUser.getRoleIds())) {
            List<SysUserRole> sysUserRoles = new ArrayList<SysUserRole>();
            String[] roleIds = StringUtils.split(sysUser.getRoleIds(), ",");
            for (String roleId : roleIds) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserRoleId(IDUtils.getNextId());
                sysUserRole.setUserId(sysUser.getUserId());
                sysUserRole.setRoleId(NumberUtils.toInt(roleId));
                sysUserRoles.add(sysUserRole);
            }
            sysUserRoleDao.insert(sysUserRoles);
        }
    }

    @Override
    public SysUserProfile getProfile(int userId) {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("userId", userId);
        return sysUserProfileDao.uniqueResult(example);
    }

    @Override
    public void updateProfile(SysUserProfile profile) {
        sysUserProfileDao.update(profile);
    }

    /**
     * 保存默认profile
     */
    private void saveDefaultProfile(int userId) {
        SysUserProfile profile = new SysUserProfile();
        profile.setLanguage(Constants.DEFAULT_PROFILE_LANG);
        profile.setProfileId(IDUtils.getNextId());
        profile.setUserId(userId);
        sysUserProfileDao.insert(profile);
    }

    /**
     * 根据用户ID，删除profile
     *
     * @param userId 用户ID
     */
    private void deleteProfile(int userId) {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("userId", userId);
        sysUserProfileDao.delete(example);
    }

    @Override
    public void updatePassword(ChangePasswordVo changePasswordVo) {
        validatorBus.validator(changePasswordVo, PasswordValidator.class);
        if (!changePasswordVo.getNewpassword().equals(changePasswordVo.getRetypepassword())) {
            throw ExceptionFactory
                    .inValidParameter("Two input password is not same");
        }
        SysUser oldUser = sysUserDao.get(changePasswordVo.getUserId());
        String oldPassword = changePasswordVo.getOldpassword();

        String encryptPassword = passwordService.getEncryptPassword(oldPassword,
                oldUser);
        if (!encryptPassword.equals(oldUser.getPassword())) {
            throw ExceptionFactory
                    .inValidParameter("The Original Password is not correct");
        }
        SysUser sysUser = new SysUser();
        sysUser.setPassword(changePasswordVo.getNewpassword());
        sysUser.setUserId(changePasswordVo.getUserId());
        sysUser.setUsername(oldUser.getUsername());
        passwordService.encryptPassword(sysUser);
        sysUserDao.update(sysUser);
    }

    public void setSysUserDao(BaseDao<Integer, SysUser> sysUserDao) {
        this.sysUserDao = sysUserDao;
    }

    public void setSysRoleDao(BaseDao<Integer, SysRole> sysRoleDao) {
        this.sysRoleDao = sysRoleDao;
    }

    public void setSysUserRoleDao(BaseDao<Integer, SysUserRole> sysUserRoleDao) {
        this.sysUserRoleDao = sysUserRoleDao;
    }

    public void setSysUserProfileDao(BaseDao<Integer, SysUserProfile> sysUserProfileDao) {
        this.sysUserProfileDao = sysUserProfileDao;
    }

    public void setValidatorBus(ValidatorBus validatorBus) {
        this.validatorBus = validatorBus;
    }

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }
}
