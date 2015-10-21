package com.edgar.module.sys.facade.impl;

import com.edgar.module.sys.facade.UserFacde;
import com.edgar.module.sys.repository.domain.SysResource;
import com.edgar.module.sys.repository.domain.SysRole;
import com.edgar.module.sys.repository.domain.SysUser;
import com.edgar.module.sys.repository.domain.SysUserRole;
import com.edgar.module.sys.service.PermissionService;
import com.edgar.module.sys.service.SysRoleService;
import com.edgar.module.sys.service.SysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统用户的facade实现
 *
 * @author Edgar
 */
@Service
public class UserFacdeImpl implements UserFacde {


    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 根据用户ID获取用户角色
     *
     * @param userId 用户ID
     * @return 角色的集合
     */
    @Override
    public List<SysRole> getRolesForUser(int userId) {

        List<SysUserRole> sysUserRoles = sysUserService.getRoles(userId);
        List<SysRole> roles = new ArrayList<SysRole>();
        for (SysUserRole sysUserRole : sysUserRoles) {
            roles.add(sysRoleService.get(sysUserRole.getRoleId()));
        }
        return roles;
    }


    @Override
    public Set<String> getPermissions(int userId) {
        List<SysRole> roles = getRolesForUser(userId);
        Set<String> permissions = new LinkedHashSet<String>();
        for (SysRole role : roles) {
            List<SysResource> sysResources = permissionService
                    .getResource(role.getRoleId());
            for (SysResource sysResource : sysResources) {
                permissions.add(sysResource.getPermission());
            }
        }

        return permissions;
    }

    @Override
    public SysUser queryByUsername(String username) {
        List<SysUser> sysUsers = sysUserService.queryByUsername(username);
        if (CollectionUtils.isNotEmpty(sysUsers)) {
            SysUser sysUser = sysUsers.get(0);
            return sysUsers.get(0);
        }
        return null;
    }

}
