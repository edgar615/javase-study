package com.edgar.module.sys.repository.domain;

import javax.annotation.Generated;

/**
 * SysRoleMenu is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class SysRoleMenu {

    private Integer menuId;

    private Integer roleId;

    private Integer roleMenuId;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleMenuId() {
        return roleMenuId;
    }

    public void setRoleMenuId(Integer roleMenuId) {
        this.roleMenuId = roleMenuId;
    }

}

