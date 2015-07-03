package com.edgar.jdbc;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/18.
 */
public class NamedParameterJdbcTemplateTest {

    private DataSource dataSource;

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/admin");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setFairQueue(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        dataSource = new DataSource();
        dataSource.setPoolProperties(p);
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @After
    public void tearDown() {
        dataSource.close();
        jdbcTemplate = null;
    }

    @Test
    public void testQueryForObject() {
        String sql = "select username from sys_user where username = :username order by user_id desc limit 1";
        Map<String, String> param = Collections.singletonMap("username", "root");
        String username = jdbcTemplate.queryForObject(sql, param, String.class);
        System.out.println(username);
    }

    @Test
    public void testQueryForObject2() {
        String sql = "select username from sys_user where username = :username order by user_id desc limit 1";
        SqlParameterSource param  = new MapSqlParameterSource("username", "root");
        String username = jdbcTemplate.queryForObject(sql, param, String.class);
        System.out.println(username);
    }

    @Test
    public void testQueryForObject3() {
        String sql = "select username from sys_user where username = :username order by user_id desc limit 1";
        SysUser sysUser = new SysUser();
        sysUser.setUsername("root");
        SqlParameterSource param  = new BeanPropertySqlParameterSource(sysUser);
        String username = jdbcTemplate.queryForObject(sql, param, String.class);
        System.out.println(username);
    }
}
