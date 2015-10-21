package com.edgar.core.auth.stateless;

import com.edgar.module.sys.repository.domain.SysRole;
import com.edgar.module.sys.repository.domain.SysUser;
import com.edgar.module.sys.repository.domain.SysUserProfile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-15
 * Time: 下午3:57
 * To change this template use File | Settings | File Templates.
 */
public class StatelessUser extends SysUser {

    /**
     * 角色列表
     */
    private List<SysRole> roles;

    /**
     * Profile
     */
    private SysUserProfile profile;

    private String accessToken;

    /**
     * 授权列表
     */
    private final Set<String> permissions = new HashSet<String>();

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public SysUserProfile getProfile() {
        return profile;
    }

    public void setProfile(SysUserProfile profile) {
        this.profile = profile;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void addPermission(String perm) {
        permissions.add(perm);
    }
}
