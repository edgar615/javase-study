package com.edgar.jdbc;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/18.
 */
public class JdbcTemplateTest {

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

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
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @After
    public void tearDown() {
        dataSource.close();
        jdbcTemplate = null;
    }

    /*
    queryForObject
     */
    @Test
    public void testQueryForObject() {
        String sql = "select count(*) from sys_user";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(count);
    }

    @Test
    public void testQueryForObject2() {
        String sql = "select username from sys_user order by user_id desc limit 1";
        String username = jdbcTemplate.queryForObject(sql, String.class);
        System.out.println(username);
    }

    @Test
    public void testQueryForObject3() {
        String sql = "select username from sys_user where username = ? order by user_id desc limit 1";
        String username = jdbcTemplate.queryForObject(sql, String.class, "root");
        System.out.println(username);
    }

    @Test
    public void testQueryForObject4() {
        String sql = "select username from sys_user where username = ? order by user_id desc limit 1";
        String username = jdbcTemplate.queryForObject(sql, new Object[] {"root"} ,String.class);
        System.out.println(username);
    }

    @Test
    public void testQueryForObject5() {
        String sql = "select * from sys_user where username = ? order by user_id desc limit 1";
        SysUser sysUser = jdbcTemplate.queryForObject(sql, new Object[] {"root"} ,new RowMapper<SysUser>() {
            @Override
            public SysUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                SysUser sysUser = new SysUser();
                sysUser.setUserId(rs.getInt("user_id"));
                sysUser.setUsername(rs.getString("username"));
                return sysUser;
            }
        });
        System.out.println(sysUser);
    }

    /*
    queryForList
     */
    @Test
    public void testQueryForList() {
        String sql = "select * from sys_user";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        System.out.println(result);
    }

    @Test
    public void testQueryForList2() {
        String sql = "select * from sys_user where username = ?";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, "root");
        System.out.println(result);
    }

    @Test
    public void testQueryForList3() {
        String sql = "select username from sys_user";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        System.out.println(result);
    }

    /*
    只能返回单个字段
     */
    @Test
    public void testQueryForList4() {
        String sql = "select username from sys_user";
        List<String> result = jdbcTemplate.queryForList(sql, String.class);
        System.out.println(result);
    }

    @Test(expected = Exception.class)
    public void testQueryForList5() {
        String sql = "select * from sys_user";
        List<SysUser> result = jdbcTemplate.queryForList(sql, SysUser.class);
        System.out.println(result);
    }


    /*
    query
     */
    @Test
    public void testQuery() {
        String sql = "select * from sys_user";
        List<SysUser> result = jdbcTemplate.query(sql, new RowMapper<SysUser>() {
            @Override
            public SysUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                SysUser sysUser = new SysUser();
                sysUser.setUserId(rs.getInt("user_id"));
                sysUser.setUsername(rs.getString("username"));
                return sysUser;
            }
        });
        System.out.println(result);
    }

    /*
    Update
     */
    @Test
    public void testUpdate() {
        String sql = "update sys_user set full_name = ? where user_id = ?";
        int result = jdbcTemplate.update(sql, "Edgar", "2454");
        System.out.println(result);
    }

    /*
    Delete
     */
    @Test
    public void testDelete() {
        String sql = "delete from sys_user where user_id = ?";
        int result = jdbcTemplate.update(sql, "2454");
        System.out.println(result);
    }

    /*
    Insert
     */
    @Test
    public void testInsert() {
        String sql = "insert into sys_user(user_id, username, password, full_name) values(?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, "2454", "2454", "2454", "2454");
        System.out.println(result);
    }

    /*
   返回自动生成的主键
    */
    @Test
    public void testInsertThenGetKey() {
        final String sql = "insert into test(name) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
                ps.setString(1, "test");
                return ps;
            }
        }, keyHolder);
        System.out.println(keyHolder.getKey());
    }

    /*
    execute
     */
    @Test
    public void testExecute() {
        String sql = "create table mytable (id integer, name varchar(100))";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testExecute2() {
        String sql = "create table mytable (id integer, name varchar(100))";
        jdbcTemplate.execute(new ConnectionCallback<Object>() {
            @Override
            public Object doInConnection(Connection con) throws SQLException, DataAccessException {
                return null;
            }
        });
    }
}
