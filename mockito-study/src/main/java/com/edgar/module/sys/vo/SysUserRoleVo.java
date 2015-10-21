package com.edgar.module.sys.vo;

import com.edgar.module.sys.repository.domain.SysUser;

/**
 * 用户角色的工具类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
public class SysUserRoleVo extends SysUser {

    private String roleIds;

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

}
