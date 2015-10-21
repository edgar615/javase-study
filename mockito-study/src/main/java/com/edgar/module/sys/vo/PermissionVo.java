package com.edgar.module.sys.vo;

import java.util.Set;


public class PermissionVo {
    private int roleId;

    private Set<Integer> permissionIds;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Set<Integer> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Set<Integer> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
