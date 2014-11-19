package com.edgar;

import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.*;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2014/11/7.
 */
public class DbUtilsTest {
    private DataSource dataSource;

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
    }

    @After
    public void tearDown() {
        dataSource.close();
    }

    @Test
    public void testBasicQuery() throws SQLException {
        ResultSetHandler<Object[]> rh = new ResultSetHandler<Object[]>() {
            @Override
            public Object[] handle(ResultSet resultSet) throws SQLException {
                if (!resultSet.next()) {
                    return null;
                }
                ResultSetMetaData metaData = resultSet.getMetaData();
                int cols = metaData.getColumnCount();
                Object[] result = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    Object r = resultSet.getObject(i + 1);
                    result[i] = r;
                }
                return result;
            }
        };
        QueryRunner runner = new QueryRunner(dataSource);
        Object[] result = runner.query("select * from sys_user where username= ? ", rh, "localhost");
        System.out.println(result);
    }

    @Test
    public void testBasicQueryWithConn() throws SQLException {
        ResultSetHandler<Object[]> rh = new ResultSetHandler<Object[]>() {
            @Override
            public Object[] handle(ResultSet resultSet) throws SQLException {
                if (!resultSet.next()) {
                    return null;
                }
                ResultSetMetaData metaData = resultSet.getMetaData();
                int cols = metaData.getColumnCount();
                Object[] result = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    Object r = resultSet.getObject(i + 1);
                    System.out.println(r);
                    result[i] = r;
                }
                return result;
            }
        };
        QueryRunner runner = new QueryRunner();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            Object[] result = runner.query(conn, "select * from sys_user where username= ? ", rh, "root");
            Assert.assertFalse(conn.isClosed());
        } finally {
            DbUtils.close(conn);
        }

    }

    /*
    读取字段的ResultSetHandler
     */

    /**
     * ScalarHandler返回第一行数据某个字段的值,如果没有指定具体的字段名，则按照字段索引返回，默认值1
     *
     * @throws SQLException
     */
    @Test
    public void testScalarListHandlerGetUsername() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<String> sysUserResultSetHandler = new ScalarHandler<String>();
        String result = runner.query("select username from sys_user ", sysUserResultSetHandler);
        System.out.println(result);
    }

    @Test
    public void testScalarListHandlerDefault() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<Integer> sysUserResultSetHandler = new ScalarHandler<Integer>();
        Integer result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        System.out.println(result);
    }

    @Test
    public void testScalarListHandlerIndex2() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<String> sysUserResultSetHandler = new ScalarHandler<String>(2);
        String result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        System.out.println(result);
    }

    @Test
    public void testScalarListHandlerUsername() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<String> sysUserResultSetHandler = new ScalarHandler<String>("username");
        String result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        System.out.println(result);
    }

    /**
     * ColumnListHandler返回查询的某个字段的集合，如果没有指定具体的字段名，则按照字段索引返回，默认值1
     *
     * @throws SQLException
     */
    @Test
    public void testColumnListResultDefault() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<List<Integer>> sysUserResultSetHandler = new ColumnListHandler<Integer>();
        List<Integer> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(1));
        System.out.println(result);
        System.out.println(result.size());
    }

    @Test
    public void testColumnListResultIndex2() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<List<String>> sysUserResultSetHandler = new ColumnListHandler<String>(2);
        List<String> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(1));
        System.out.println(result);
        System.out.println(result.size());
    }

    @Test
    public void testColumnListResultUsername() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<List<String>> sysUserResultSetHandler = new ColumnListHandler<String>("username");
        List<String> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(1));
        System.out.println(result);
        System.out.println(result.size());
    }

    /*
    每行数据以map形式返回的ResultSetHandler
     */

    /**
     * MapHandler以{字段名：字段值}的Map的形式返回第一行数据
     *
     * @throws SQLException
     */
    @Test
    public void testMapHandler() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<Map<String, Object>> sysUserResultSetHandler = new MapHandler();
        Map<String, Object> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get("full_name"));
        System.out.println(result);
        System.out.println(result.size());
    }

    /**
     * MapListHandler以{字段名：字段值}的Map的形式返回所有的数据集合
     *
     * @throws SQLException
     */
    @Test
    public void testMapListHandler() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<List<Map<String, Object>>> sysUserResultSetHandler = new MapListHandler();
        List<Map<String, Object>> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(0).get("full_name"));
        System.out.println(result);
        System.out.println(result.size());
    }

    /*
    每行数据以object[]形式返回的ResultSetHandler
     */

    /**
     * ArrayHandler使用Object[]数组返回第一行数据
     *
     * @throws SQLException
     */
    @Test
    public void testArrayResult() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<Object[]> sysUserResultSetHandler = new ArrayHandler();
        Object[] result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.length > 0);
    }

    /**
     * ArrayListHandler<Object[]>数组返回所有数据
     *
     * @throws SQLException
     */
    @Test
    public void testArrayListResult() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<List<Object[]>> sysUserResultSetHandler = new ArrayListHandler();
        List<Object[]> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        System.out.println(result.get(0).length);
        Assert.assertFalse(result.isEmpty());
    }

    /*
     * 每行数据以JavaBean的形式返回
     */

    /**
     * BeanHandler返回第一行数据到JavaBean
     *
     * @throws SQLException
     */
    @Test
    public void testBeanResult() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<SysUser> sysUserResultSetHandler = new BeanHandler<SysUser>(SysUser.class);
        SysUser sysUser = runner.query("select * from sys_user where username = ?", sysUserResultSetHandler, "root");
        Assert.assertNotNull(sysUser);
        Assert.assertEquals("root", sysUser.getUsername());
        System.out.println(sysUser.getFullName());
    }

    /**
     * 返回所有数据的JAVABEAN集合
     *
     * @throws SQLException
     */
    @Test
    public void testBeanListResult() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<List<SysUser>> sysUserResultSetHandler = new BeanListHandler<SysUser>(SysUser.class);
        List<SysUser> result = runner.query("select * from sys_user", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
    }

    /*
   自定义BeanProcessor
    */
    @Test
    public void testBeanResultHump() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<SysUser> sysUserResultSetHandler = new BeanHandler<SysUser>(SysUser.class, new BasicRowProcessor(new GenerousBeanProcessor()));
        SysUser sysUser = runner.query("select * from sys_user where username = ?", sysUserResultSetHandler, "root");
        Assert.assertNotNull(sysUser);
        Assert.assertEquals("root", sysUser.getUsername());
        System.out.println(sysUser.getFullName());
    }

    /*
    以map的形式返回结果集
     */

    /**
     * BeanMapHandler以Map的形式返回数据，主键为Key,JavaBean为值
     *
     * @throws SQLException
     */
    @Test
    public void testBeanMapResultDefault() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<Map<Integer, SysUser>> sysUserResultSetHandler = new BeanMapHandler<Integer, SysUser>(SysUser.class);
        Map<Integer, SysUser> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(1));
        System.out.println(result.get(1).getUsername());
        System.out.println(result);
    }

    @Test
    public void testBeanMapResultIndex2() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<Map<String, SysUser>> sysUserResultSetHandler = new BeanMapHandler<String, SysUser>(SysUser.class, 2);
        Map<String, SysUser> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get("zyz2"));
        System.out.println(result.get("zyz2").getUsername());
        System.out.println(result);
    }

    @Test
    public void testBeanMapResultUsername() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<Map<String, SysUser>> sysUserResultSetHandler = new BeanMapHandler<String, SysUser>(SysUser.class, "username");
        Map<String, SysUser> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get("zyz2"));
        System.out.println(result.get("zyz2").getUsername());
        System.out.println(result);
    }

    /**
     * KeyedHandler以Map的形式返回数据，主键为Key,{字段名：字段值}的Map对象为值
     *
     * @throws SQLException
     */
    @Test
    public void testKeyedHandlerDefault() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<Map<Integer, Map<String, Object>>> sysUserResultSetHandler = new KeyedHandler<Integer>();
        Map<Integer, Map<String, Object>> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(1));
        System.out.println(result);
        System.out.println(result.get(1).get("full_name"));
        System.out.println(result.size());
    }

    @Test
    public void testKeyedHandlerIndex2() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<Map<String, Map<String, Object>>> sysUserResultSetHandler = new KeyedHandler<String>(2);
        Map<String, Map<String, Object>> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get("zyz"));
        System.out.println(result);
        System.out.println(result.get("zyz").get("full_name"));
        System.out.println(result.size());
    }

    @Test
    public void testKeyedHandlerUsername() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<Map<String, Map<String, Object>>> sysUserResultSetHandler = new KeyedHandler<String>("username");
        Map<String, Map<String, Object>> result = runner.query("select * from sys_user ", sysUserResultSetHandler);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get("zyz"));
        System.out.println(result);
        System.out.println(result.get("zyz").get("full_name"));
        System.out.println(result.size());
    }

    /*
    insert update delete
     */

    @Test
    public void testInsert() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        int result = runner.update("insert into sys_user(user_id, username, password, full_name) values(?, ?, ?, ?)", 2, "test", "test", "test");
        Assert.assertEquals(1, result);
    }

    @Test
    public void testUpdate() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        int result = runner.update("update sys_user set username = ? where user_id = ?", "test2", 2);
        Assert.assertEquals(1, result);
    }

    @Test
    public void testDelete() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        int result =  runner.update("delete from sys_user where user_id = ?", 2);
        Assert.assertEquals(1, result);
    }

    @Test
    public void testInsertWithGeneratedKey() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<Long> r = new ScalarHandler<Long>();
        Long result = runner.insert("insert into test(name) values(?)", r, "test");
        System.out.println(result);
    }

    @Test
    public void testUpdateBatch() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        Object[][] params = new Object[2][2];
        params[0][0] = "test3";
        params[0][1] = "1";
        params[1][0] = "test4";
        params[1][1] = "2";
        int[] result = runner.batch("update sys_user set username = ? where user_id = ?", params);
        System.out.println(result[0]);
        System.out.println(result[1]);
    }

    @Test
    public void testInsertBatch() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<List<Long>> r = new ColumnListHandler<Long>();
        Object[][] params = new Object[2][1];
        params[0][0] = "test3";
        params[1][0] = "test4";
        List<Long> result = runner.insertBatch("insert into test(name) values(?)", r, params);
        System.out.println(result);
    }

    /*
    异步执行
     */
    @Test
    public void testAsync() throws SQLException, ExecutionException, InterruptedException {
        AsyncQueryRunner runner = new AsyncQueryRunner(Executors.newCachedThreadPool(), new QueryRunner(dataSource));
        Future<Integer> future = runner.update("update sys_user set username = ? where user_id = ?", "test", 1);
        int result = future.get();
        System.out.println(result);
        Assert.assertEquals(1, result);
    }

    /*
    事务
     */
    @Test
    public void testTransaction() throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            runner.update(conn, "insert into sys_user(user_id, username, password, full_name) values(?, ?, ?, ?)", 2, "test", "test", "test");
            conn.commit();
            throw new RuntimeException("rollback");
        } catch (Exception e) {
            DbUtils.rollback(conn);
        } finally {
            DbUtils.close(conn);
        }
    }

}
