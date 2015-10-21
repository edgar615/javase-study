package com.edgar.module.sys.web;

import com.edgar.core.repository.QueryExample;
import com.edgar.core.shiro.AuthHelper;
import com.edgar.core.view.ResponseMessage;
import com.edgar.module.sys.service.PermissionService;
import com.edgar.module.sys.service.SysMenuService;
import com.edgar.module.sys.vo.PermissionVo;
import com.edgar.module.sys.vo.SysMenuVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色授权的rest
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Controller
@RequestMapping("/sys/permission")
public class SysPermissionResource {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 保存资源授权
     *
     * @param permissionVo 资源授权的对象
     * @return 如果授权成功，返回1
     */
    @AuthHelper("Save Permisison")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView savePermission(@RequestBody PermissionVo permissionVo) {
        permissionService.savePermission(permissionVo);
        return ResponseMessage.success();
    }

    /**
     * 查询角色的资源授权
     *
     * @param roleId 角色ID
     * @return 角色资源集合
     */
    @AuthHelper("Query Permisison")
    @RequestMapping(value = "/menu/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SysMenuVo> getMenus(@PathVariable("roleId") int roleId) {
        Set<Integer> sysRoleMenus = new HashSet<Integer>(
                permissionService.getMenu(roleId));
        List<SysMenuVo> sysMenuVos = sysMenuService.query(QueryExample
                .newInstance());
        setChecked(sysRoleMenus, sysMenuVos);
        return sysMenuVos;
    }

    private void setChecked(Set<Integer> sysRoleMenus,
                            List<SysMenuVo> sysMenuVos) {
        for (SysMenuVo sysMenuVo : sysMenuVos) {
            sysMenuVo.setChecked(sysRoleMenus.contains(sysMenuVo.getMenuId()));
            if (CollectionUtils.isNotEmpty(sysMenuVo.getChildren())) {
                setChecked(sysRoleMenus, sysMenuVo.getChildren());
            }
        }
    }
}
