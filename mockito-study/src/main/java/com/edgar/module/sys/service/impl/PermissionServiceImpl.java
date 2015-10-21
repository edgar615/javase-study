package com.edgar.module.sys.service.impl;

import com.edgar.core.repository.BaseDao;
import com.edgar.core.repository.IDUtils;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.util.ExceptionFactory;
import com.edgar.module.sys.repository.domain.*;
import com.edgar.module.sys.service.PermissionService;
import com.edgar.module.sys.vo.PermissionVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private BaseDao<Integer, SysRoleMenu> sysRoleMenuDao;

    @Autowired
    private BaseDao<Integer, SysRole> sysRoleDao;

    @Autowired
    private BaseDao<Integer, SysResource> sysResourceDao;

    @Autowired
    private BaseDao<Integer, SysRoute> sysRouteDao;

    @Autowired
    private BaseDao<Integer, SysMenu> sysMenuDao;

    @Autowired
    private BaseDao<Integer, SysMenuRes> sysMenuResDao;

    @Autowired
    private BaseDao<Integer, SysMenuRoute> sysMenuRouteDao;

    @Autowired
    private BaseDao<Integer, SysRoleRoute> sysRoleRouteDao;

    @Autowired
    private BaseDao<Integer, SysRoleRes> sysRoleResDao;

    @Override
    public List<Integer> getMenu(int roleId) {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("roleId", roleId);
        example.addField("menuId");
        return sysRoleMenuDao.querySingleColumn(example,
                Integer.class);
    }

    @Override
    public List<SysRoute> getRoute(int roleId) {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("roleId", roleId);
        example.addField("menuId");
        List<Integer> menuIds = sysRoleMenuDao.querySingleColumn(example,
                Integer.class);
        Set<Integer> routeIds = new HashSet<Integer>();
        for (Integer menuId : menuIds) {
            example.clear();
            example.equalsTo("menuId", menuId);
            example.addField("routeId");
            routeIds.addAll(sysMenuRouteDao.querySingleColumn(example,
                    Integer.class));
        }
        example.clear();
        example.equalsTo("roleId", roleId);
        example.addField("routeId");
        routeIds.addAll(sysRoleRouteDao.querySingleColumn(example,
                Integer.class));
        List<SysRoute> sysRoutes = new ArrayList<SysRoute>();
        for (Integer routeId : routeIds) {
            sysRoutes.add(sysRouteDao.get(routeId));
        }
        return sysRoutes;
    }

    @Override
    public List<SysResource> getResource(int roleId) {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("roleId", roleId);
        example.addField("menuId");
        List<Integer> menuIds = sysRoleMenuDao.querySingleColumn(example,
                Integer.class);
        Set<Integer> resourceIds = new HashSet<Integer>();
        for (Integer menuId : menuIds) {
            example.clear();
            example.equalsTo("menuId", menuId);
            example.addField("resourceId");
            resourceIds.addAll(sysMenuResDao.querySingleColumn(example,
                    Integer.class));
        }
        example.clear();
        example.equalsTo("roleId", roleId);
        example.addField("resourceId");
        resourceIds.addAll(sysRoleResDao.querySingleColumn(example,
                Integer.class));
        List<SysResource> sysResources = new ArrayList<SysResource>();
        for (Integer resourceId : resourceIds) {
            sysResources.add(sysResourceDao.get(resourceId));
        }
        return sysResources;
    }

    @Override
    @Transactional
    public void savePermission(PermissionVo permissionVo) {
        int roleId = permissionVo.getRoleId();
        deleteByRole(roleId);
        Set<Integer> permIds = permissionVo.getPermissionIds();
        if (CollectionUtils.isEmpty(permIds)) {
            return;
        }
        Set<Integer> unModifiablePermIds = Collections.unmodifiableSet(permIds);
        saveRoleMenu(roleId, unModifiablePermIds);
        checkRole(permissionVo.getRoleId());
    }

    /**
     * 保存角色菜单关联
     *
     * @param roleId  角色ID
     * @param menuIds 菜单的ID集合
     */
    private void saveRoleMenu(int roleId, Set<Integer> menuIds) {
        List<SysRoleMenu> sysRoleMenus = new ArrayList<SysRoleMenu>();
        Set<Integer> parentIds = new HashSet<Integer>();
        for (Integer menuId : menuIds) {
            sysRoleMenus.add(newSysRoleMenu(roleId, menuId));
            SysMenu sysMenu = checkSysMenu(menuId);
            if (sysMenu.getParentId() != null && sysMenu.getParentId() > 0) {
                parentIds.add(sysMenu.getParentId());
            }
        }

        for (Integer menuId : parentIds) {
            sysRoleMenus.add(newSysRoleMenu(roleId, menuId));
            checkSysMenu(menuId);
        }
        sysRoleMenuDao.insert(sysRoleMenus);
        parentIds.addAll(menuIds);
    }

    /**
     * 检查菜单是否存在，如果菜单不存在，则抛出SystemException异常
     *
     * @param menuId 菜单ID
     * @return 菜单
     */
    private SysMenu checkSysMenu(int menuId) {
        SysMenu sysMenu = sysMenuDao.get(menuId);
        if (sysMenu == null) {
            throw ExceptionFactory.isNull();
        }
        return sysMenu;
    }

    /**
     * 创建角色菜单关联
     *
     * @param roleId 角色ID
     * @param menuId 菜单ID
     * @return 角色菜单关联
     */
    private SysRoleMenu newSysRoleMenu(int roleId, int menuId) {
        SysRoleMenu sysRoleRoute = new SysRoleMenu();
        sysRoleRoute.setRoleMenuId(IDUtils.getNextId());
        sysRoleRoute.setRoleId(roleId);
        sysRoleRoute.setMenuId(menuId);
        return sysRoleRoute;
    }

    /**
     * 根据角色删除资源权限
     *
     * @param roleId 角色ID
     */
    private void deleteByRole(int roleId) {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("roleId", roleId);
        sysRoleMenuDao.delete(example);
    }

    /**
     * 检查角色是否存在，如果不存在，则抛出空的异常
     *
     * @param roleId 角色ID
     */
    private void checkRole(int roleId) {
        SysRole sysRole = sysRoleDao.get(roleId);
        if (sysRole == null) {
            throw ExceptionFactory.isNull();
        }
    }

    public void setSysRoleMenuDao(BaseDao<Integer, SysRoleMenu> sysRoleMenuDao) {
        this.sysRoleMenuDao = sysRoleMenuDao;
    }

    public void setSysRoleDao(BaseDao<Integer, SysRole> sysRoleDao) {
        this.sysRoleDao = sysRoleDao;
    }

    public void setSysResourceDao(BaseDao<Integer, SysResource> sysResourceDao) {
        this.sysResourceDao = sysResourceDao;
    }

    public void setSysRouteDao(BaseDao<Integer, SysRoute> sysRouteDao) {
        this.sysRouteDao = sysRouteDao;
    }

    public void setSysMenuDao(BaseDao<Integer, SysMenu> sysMenuDao) {
        this.sysMenuDao = sysMenuDao;
    }

    public void setSysMenuResDao(BaseDao<Integer, SysMenuRes> sysMenuResDao) {
        this.sysMenuResDao = sysMenuResDao;
    }

    public void setSysMenuRouteDao(BaseDao<Integer, SysMenuRoute> sysMenuRouteDao) {
        this.sysMenuRouteDao = sysMenuRouteDao;
    }

    public void setSysRoleRouteDao(BaseDao<Integer, SysRoleRoute> sysRoleRouteDao) {
        this.sysRoleRouteDao = sysRoleRouteDao;
    }

    public void setSysRoleResDao(BaseDao<Integer, SysRoleRes> sysRoleResDao) {
        this.sysRoleResDao = sysRoleResDao;
    }
}
