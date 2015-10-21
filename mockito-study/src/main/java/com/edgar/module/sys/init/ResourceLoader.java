package com.edgar.module.sys.init;

import com.edgar.core.exception.SystemException;
import com.edgar.core.init.AppInitializer;
import com.edgar.core.init.Initialization;
import com.edgar.core.repository.BaseDao;
import com.edgar.core.repository.IDUtils;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.shiro.AuthHelper;
import com.edgar.core.shiro.FilterChainDefinitionsLoader;
import com.edgar.core.util.Constants;
import com.edgar.module.sys.repository.domain.*;
import com.edgar.module.sys.service.PasswordService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Controller
public class ResourceLoader implements Initialization {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AppInitializer.class);

    private static final String ROOT = Constants.ROOT_ROLE_NAME;
    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    private BaseDao<Integer, SysResource> sysResourceDao;

    @Autowired
    private BaseDao<Integer, SysRole> sysRoleDao;

    @Autowired
    private BaseDao<Integer, SysRoleRes> sysRoleResDao;

    @Autowired
    private BaseDao<Integer, SysUser> sysUserDao;

    @Autowired
    private BaseDao<Integer, SysUserRole> sysUserRoleDao;

    @Autowired
    private BaseDao<Integer, SysMenu> sysMenuDao;

    @Autowired
    private BaseDao<Integer, SysRoleMenu> sysRoleMenuDao;

    @Autowired
    private BaseDao<Integer, SysRoute> sysRouteDao;

    @Autowired
    private BaseDao<Integer, SysRoleRoute> sysRoleRouteDao;

    @Autowired
    private BaseDao<Integer, SysUserProfile> sysUserProfileDao;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    private FilterChainDefinitionsLoader filterChainDefinitionsLoader;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    @Transactional
    public void init() throws SystemException {
        LOGGER.info("/*****load resource start*****/");
        List<SysResource> resources = sysResourceDao.query(QueryExample
                .newInstance());
        Map<String, SysResource> resourceMap = new HashMap<String, SysResource>();

        for (SysResource sysResource : resources) {
            resourceMap.put("[" + sysResource.getMethod() + "]"
                    + getNewUrl(sysResource.getUrl()), sysResource);
        }
        Set<String> existResourceKey = insertResource(resourceMap);
        deleteNotExistsReource(resourceMap, existResourceKey);
        SysRole rootRole = getRootRole();
        saveRootPermission(rootRole);
        saveRootMenu(rootRole);
        saveRootRoute(rootRole);
        createRootUser(rootRole);
        refreshShiro();
        LOGGER.info("/*****load resource finished*****/");
    }

    private void refreshShiro() {
        AbstractShiroFilter shiroFilter;

        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
                    .getObject();
        } catch (Exception e) {
            throw new RuntimeException("get shiroFilter failed");
        }

        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                .getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                .getFilterChainManager();
        // 清空老的权限控制
        manager.getFilterChains().clear();
        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
        shiroFilterFactoryBean
                .setFilterChainDefinitionMap(filterChainDefinitionsLoader
                        .loadDefinitions());
        // 重新构建生成
        Map<String, String> chains = shiroFilterFactoryBean
                .getFilterChainDefinitionMap();
        for (Map.Entry<String, String> entry : chains.entrySet()) {
            String url = entry.getKey();
            String chainDefinition = entry.getValue();// .trim().replace(" ",
            // "");
            manager.createChain(url, chainDefinition);
        }
    }

    private String getNewUrl(String url) {
        String newUrl = url.replaceAll("\\{[0-9a-zA-Z_]+\\}", "**");
        // if (StringUtils.endsWith(newUrl, "/**")) {
        // newUrl = StringUtils.substringBeforeLast(newUrl, "/**");
        // }
        // if (StringUtils.endsWith(newUrl, "/pagination")) {
        // // newUrl = StringUtils.substringBeforeLast(newUrl, "/pagination");
        // newUrl = url.replaceAll("/pagination", "/**");
        // }
        if (!StringUtils.endsWith(newUrl, "/**")) {
            // newUrl = StringUtils.substringBeforeLast(newUrl, "/pagination");
            newUrl = newUrl + "/**";
        }
        return newUrl;
    }

    private void saveRootRoute(SysRole rootRole) {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("roleId", rootRole.getRoleId());
        List<SysRoleRoute> sysRoleResources = sysRoleRouteDao.query(example);
        Set<Integer> routeIds = new HashSet<Integer>();
        for (SysRoleRoute sysRoleResource : sysRoleResources) {
            routeIds.add(sysRoleResource.getRouteId());
        }
        List<SysRoute> routes = sysRouteDao.query(QueryExample.newInstance());
        for (SysRoute route : routes) {
            if (!routeIds.contains(route.getRouteId())) {
                SysRoleRoute sysRoleRoute = new SysRoleRoute();
                sysRoleRoute.setRoleRouteId(IDUtils.getNextId());
                sysRoleRoute.setRouteId(route.getRouteId());
                sysRoleRoute.setRoleId(rootRole.getRoleId());
                sysRoleRouteDao.insert(sysRoleRoute);
            }
        }
    }

    private void saveRootMenu(SysRole rootRole) {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("roleId", rootRole.getRoleId());
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuDao.query(example);
        Set<Integer> menuIds = new HashSet<Integer>();
        for (SysRoleMenu sysRoleResource : sysRoleMenus) {
            menuIds.add(sysRoleResource.getMenuId());
        }
        List<SysMenu> routes = sysMenuDao.query(QueryExample.newInstance());
        for (SysMenu route : routes) {
            if (!menuIds.contains(route.getMenuId())) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleMenuId(IDUtils.getNextId());
                sysRoleMenu.setMenuId(route.getMenuId());
                sysRoleMenu.setRoleId(rootRole.getRoleId());
                sysRoleMenuDao.insert(sysRoleMenu);
            }
        }

    }

    private SysRole getRootRole() {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("roleCode", ROOT);
        SysRole root = sysRoleDao.uniqueResult(example);
        if (root == null) {
            root = new SysRole();
            root.setRoleId(IDUtils.getNextId());
            root.setRoleName(ROOT);
            root.setRoleCode(ROOT);
            root.setIsRoot(true);
            sysRoleDao.insert(root);
        }
        return root;
    }

    private void createRootUser(SysRole rootRole) {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("roleId", rootRole.getRoleId());
        List<SysUserRole> sysUserRoles = sysUserRoleDao.query(example);
        if (sysUserRoles.isEmpty()) {
            SysUser rootUser = createUser();
            createUserRole(rootRole, rootUser);
        }

    }

    private SysUser createUser() {
        SysUser rootUser = new SysUser();
        rootUser.setUserId(IDUtils.getNextId());
        rootUser.setUsername(ROOT);
        rootUser.setPassword("csstrd");
        rootUser.setIsRoot(true);
        rootUser.setFullName("Adminiatrator");
        passwordService.encryptPassword(rootUser);
        sysUserDao.insert(rootUser);

        SysUserProfile profile = new SysUserProfile();
        profile.setLanguage(Constants.DEFAULT_PROFILE_LANG);
        profile.setUserId(rootUser.getUserId());
        profile.setProfileId(IDUtils.getNextId());
        sysUserProfileDao.insert(profile);

        return rootUser;
    }

    private void createUserRole(SysRole rootRole, SysUser rootUser) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserRoleId(IDUtils.getNextId());
        sysUserRole.setRoleId(rootRole.getRoleId());
        sysUserRole.setUserId(rootUser.getUserId());
        sysUserRoleDao.insert(sysUserRole);
    }

    private void saveRootPermission(SysRole rootRole) {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("roleId", rootRole.getRoleId());
        List<SysRoleRes> sysRoleReses = sysRoleResDao.query(example);
        Set<Integer> resourceIds = new HashSet<Integer>();
        for (SysRoleRes sysRoleRes : sysRoleReses) {
            resourceIds.add(sysRoleRes.getResourceId());
        }
        List<SysResource> resources = sysResourceDao.query(QueryExample
                .newInstance());
        for (SysResource sysResource : resources) {
            if (!resourceIds.contains(sysResource.getResourceId())) {
                SysRoleRes sysRoleRes = new SysRoleRes();
                sysRoleRes.setRoleResId(IDUtils.getNextId());
                sysRoleRes.setResourceId(sysResource.getResourceId());
                sysRoleRes.setRoleId(rootRole.getRoleId());
                sysRoleResDao.insert(sysRoleRes);
            }
        }
    }

    private Set<String> insertResource(Map<String, SysResource> resourceMap) {
        Map<RequestMappingInfo, HandlerMethod> methods = handlerMapping
                .getHandlerMethods();
        Collection<RequestMappingInfo> infos = methods.keySet();
        Set<String> existResourceKey = new HashSet<String>();
        for (RequestMappingInfo info : infos) {
            for (String s : info.getPatternsCondition().getPatterns()) {
                String newUrl = getNewUrl(s);
                Set<RequestMethod> requestMethods = info.getMethodsCondition()
                        .getMethods();
                for (RequestMethod requestMethod : requestMethods) {
                    String key = "[" + requestMethod.name().toUpperCase() + "]"
                            + newUrl;
                    if (resourceMap.containsKey(key)) {
                        SysResource sysResource = resourceMap.get(key);
                        HandlerMethod handlerMethod = methods.get(info);
                        AuthHelper helper = handlerMethod
                                .getMethodAnnotation(AuthHelper.class);
                        if (helper != null) {
                            if (sysResource.getIsRoot()) {
                                if (!helper.isRoot()) {
                                    sysResource.setIsRoot(false);
                                    sysResourceDao.update(sysResource);
                                }
                            }
                        }
                    } else if (!resourceMap.containsKey(key)
                            && !existResourceKey.contains(key)) {
                        SysResource sysResource = new SysResource();
                        sysResource.setResourceId(IDUtils.getNextId());
                        sysResource.setMethod(requestMethod.name());
                        sysResource.setUrl(newUrl);
                        String permission = StringUtils.replace(newUrl, "/",
                                ":");
                        permission = StringUtils
                                .substringAfter(permission, ":");
                        permission = StringUtils.substringBeforeLast(
                                permission, ":**");
                        switch (requestMethod) {
                            case GET:
                                permission = permission + ":read";
                                break;
                            case POST:
                                permission = permission + ":create";
                                break;
                            case PUT:
                                permission = permission + ":update";
                                break;
                            case DELETE:
                                permission = permission + ":delete";
                                break;
                            case TRACE:
                                permission = permission + ":read";
                                break;
                            default:
                                break;
                        }
                        sysResource.setPermission(permission);
                        HandlerMethod handlerMethod = methods.get(info);
                        AuthHelper helper = handlerMethod
                                .getMethodAnnotation(AuthHelper.class);
                        if (helper != null) {
                            sysResource.setResourceName(helper.value());
                            sysResource.setIsRoot(helper.isRoot());
                            switch (helper.type()) {
                                case REST:
                                    sysResource
                                            .setAuthType(Constants.AUTH_TYPE_REST);
                                    break;
                                case AUTHC:
                                    sysResource
                                            .setAuthType(Constants.AUTH_TYPE_AUTHC);
                                    break;
                                case ANON:
                                    sysResource
                                            .setAuthType(Constants.AUTH_TYPE_ANON);
                                    break;
                                case SSL:
                                    sysResource
                                            .setAuthType(Constants.AUTH_TYPE_SSL);
                                    break;
                                default:
                                    sysResource
                                            .setAuthType(Constants.AUTH_TYPE_REST);
                                    break;
                            }
                            if (sysResource.getIsRoot() == null) {
                                sysResource.setIsRoot(false);
                            }
                            if (StringUtils.isBlank(sysResource.getAuthType())) {
                                sysResource.setAuthType(Constants.AUTH_TYPE_ANON);
                            }
                            sysResourceDao.insert(sysResource);
                        }
                    }
                    existResourceKey.add(key);
                }
            }
        }
        return existResourceKey;
    }

    private void deleteNotExistsReource(Map<String, SysResource> resourceMap,
                                        Set<String> existResourceKey) {
        for (String key : resourceMap.keySet()) {
            if (!existResourceKey.contains(key)) {
                SysResource resource = resourceMap.get(key);
                sysResourceDao.deleteByPk(resource.getResourceId());
                QueryExample example = QueryExample.newInstance();
                example.equalsTo("resourceId", resource.getResourceId());
                sysRoleResDao.delete(example);
            }
        }
    }

}
