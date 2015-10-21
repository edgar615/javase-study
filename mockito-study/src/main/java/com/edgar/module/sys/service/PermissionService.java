package com.edgar.module.sys.service;

import com.edgar.module.sys.repository.domain.SysResource;
import com.edgar.module.sys.repository.domain.SysRoute;
import com.edgar.module.sys.vo.PermissionVo;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface PermissionService {

    /**
     * 根据角色查询角色授权的菜单
     *
     * @param roleId 角色ID
     * @return 角色菜单集合
     */
    List<Integer> getMenu(@Min(1) int roleId);

    /**
     * 根据角色查询角色授权的路由
     *
     * @param roleId 角色ID
     * @return 角色路由集合
     */
    List<SysRoute> getRoute(@Min(1) int roleId);

    /**
     * 根据角色查询角色授权的资源
     *
     * @param roleId 角色ID
     * @return 角色资源集合
     */
    List<SysResource> getResource(int roleId);


    /**
     * 保存资源权限
     *
     * @param permissionVo PermissionVo
     */
    void savePermission(@NotNull PermissionVo permissionVo);

}
