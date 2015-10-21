package com.edgar.core.mvc;

import com.edgar.core.auth.stateless.StatelessUser;
import com.edgar.core.shiro.AuthHelper;
import com.edgar.core.shiro.AuthType;
import com.edgar.core.util.Constants;
import com.edgar.core.util.ExceptionFactory;
import com.edgar.module.sys.repository.domain.SysMenu;
import com.edgar.module.sys.repository.domain.SysRoute;
import com.edgar.module.sys.repository.domain.SysUserProfile;
import com.edgar.module.sys.repository.domain.SysUserRole;
import com.edgar.module.sys.service.PermissionService;
import com.edgar.module.sys.service.SysMenuService;
import com.edgar.module.sys.service.SysRouteService;
import com.edgar.module.sys.service.SysUserService;
import com.edgar.module.sys.vo.AngularRoute;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.*;

/**
 * 路由的rest接口
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Controller
@RequestMapping("/index")
public class IndexResource {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRouteService sysRouteService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据用户权限返回路由
     *
     * @return 路由的集合
     */
    private List<AngularRoute> getRoutes(Set<Integer> roleIds) {
        List<AngularRoute> angularRoutes = new ArrayList<AngularRoute>();
        List<SysRoute> routes = new ArrayList<SysRoute>();

        for (Integer roleId : roleIds) {
            routes.addAll(permissionService.getRoute(roleId));
        }

        for (SysRoute sysRoute : routes) {
            AngularRoute angularRoute = new AngularRoute();
            angularRoute.setUrl(sysRoute.getUrl());
            angularRoute.setName(sysRoute.getName());
            angularRoutes.add(angularRoute);
        }
        return angularRoutes;
    }

    /**
     * 根据用户权限返回菜单
     *
     * @return 菜单的集合
     */
    @AuthHelper(value = "Query User Profile", isRoot = true, type = AuthType.AUTHC)
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    @ResponseBody
    public Map<String, Object> getUserData() {
        StatelessUser user = (StatelessUser) RequestContextHolder.currentRequestAttributes().getAttribute(Constants.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        Set<Integer> roleIds = new LinkedHashSet<Integer>();
        List<SysUserRole> roles = sysUserService.getRoles(user.getUserId());

        for (SysUserRole sysUserRole : roles) {
            roleIds.add(sysUserRole.getRoleId());
        }
        if (roleIds.isEmpty()) {
            throw ExceptionFactory.isNull();
        }
        Map<String, Object> data = new HashMap<String, Object>();
        SysUserProfile profile = sysUserService.getProfile(user
                .getUserId());
        user.setProfile(profile);

        List<SysMenu> menus = new ArrayList<SysMenu>();
        for (Integer roleId : roleIds) {

            Set<Integer> menuIds = new HashSet<Integer>(
                    permissionService.getMenu(roleId));
            for (Integer menuId : menuIds) {
                SysMenu menu = sysMenuService.get(menuId);
                menus.add(menu);
                if (StringUtils.isNotBlank(menu.getPermission())) {
                    user.addPermission(menu.getPermission());
                }
            }
        }
        data.put("menus", menus);
        data.put("user", user);
        data.put("routes", getRoutes(roleIds));
        return data;
    }

}
