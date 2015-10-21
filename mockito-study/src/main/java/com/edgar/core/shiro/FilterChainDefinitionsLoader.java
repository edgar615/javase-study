package com.edgar.core.shiro;

import com.edgar.core.util.Constants;
import com.edgar.module.sys.repository.domain.SysResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态获取数据中的资源权限
 *
 * @author Edgar Zhang
 * @version 1.0
 */
public class FilterChainDefinitionsLoader {

    /**
     * 查询资源授权的SQL
     */
    private static final String URLS_QUERY = "select url, permission, auth_type from sys_resource order by url desc";

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 从数据库中加载资源
     *
     * @return 资源权限的map对象
     */
    public Map<String, String> loadDefinitions() {

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        setStaticAuth(filterChainDefinitionMap);
        filterChainDefinitionMap.putAll(getAuthFromDB());
        return filterChainDefinitionMap;
    }

    /**
     * 设置静态资源的权限
     *
     * @param filterChainDefinitionMap 资源授权的map
     */
    private void setStaticAuth(Map<String, String> filterChainDefinitionMap) {
        filterChainDefinitionMap.put("/login.html", "anon");
        filterChainDefinitionMap.put("/index.html", "anon");
        filterChainDefinitionMap.put("/app/**", "anon");
        filterChainDefinitionMap.put("/assets/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/monitoring", "anon");
    }

    /**
     * 查询数据库中资源的授权
     *
     * @return 资源授权
     */
    private Map<String, String> getAuthFromDB() {
        Map<String, String> authcMap = new LinkedHashMap<String, String>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<SysResource> resources = jdbcTemplate.query(URLS_QUERY,
                BeanPropertyRowMapper.newInstance(SysResource.class));
        for (SysResource resource : resources) {
            if (Constants.AUTH_TYPE_SSL.equals(resource.getAuthType())) {
                authcMap.put(resource.getUrl(), "ssl, anon");
            } else if (Constants.AUTH_TYPE_REST.equals(resource.getAuthType())) {
                // map.put(resource.getUrl(), resource.getPermission());
                String permission = StringUtils.substringBeforeLast(
                        resource.getPermission(), ":");
                authcMap.put(resource.getUrl(), "replayAttack,statelessAuthc,rest[" + permission + "]");
            } else if (Constants.AUTH_TYPE_AUTHC.equals(resource.getAuthType())) {
                authcMap.put(resource.getUrl(), "replayAttack,statelessAuthc");
            } else {
                authcMap.put(resource.getUrl(), "anon");
            }
        }
        return authcMap;
    }
}
