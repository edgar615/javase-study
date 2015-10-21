package com.edgar.module.sys.repository.domain;

import javax.annotation.Generated;

/**
 * SysUserRole is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class SysUserRole {

    private Integer roleId;

    private Integer userId;

    private Integer userRoleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

}

