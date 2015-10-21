package com.edgar.module.sys.facade;

import com.edgar.module.sys.repository.domain.SysRole;
import com.edgar.module.sys.repository.domain.SysUser;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;

/**
 * 系统用户的facade接口
 */
@Validated
public interface UserFacde {

    /**
     * 获取用户的授权
     *
     * @param userId 用户ID
     * @return 授权的集合
     */
    Set<String> getPermissions(int userId);

    /**
     * 根据用户名返回用户
     *
     * @param username 用户名
     * @return 系统用户
     */
    SysUser queryByUsername(String username);

    /**
     * 根据用户ID返回角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> getRolesForUser(int userId);
}
