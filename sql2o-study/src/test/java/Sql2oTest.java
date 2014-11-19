import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.ResultSetIterable;
import org.sql2o.Sql2o;
import org.sql2o.converters.Converter;
import org.sql2o.converters.ConverterException;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/17.
 */
public class Sql2oTest {

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
    public void testBasicQuery() {
        System.out.println(dataSource.getActive());
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            List<SysUser> sysUsers = connection.createQuery("select user_id as userId, username, password, full_name as fullName from sys_user")
                    .executeAndFetch(SysUser.class);
            System.out.println(dataSource.getActive());
            System.out.println(sysUsers);
        }
        System.out.println(dataSource.getActive());
    }

    /*
    返回POJO
     */
    @Test
    public void testReturnPojoList() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            List<SysUser> sysUsers = connection.createQuery("select user_id as userId, username, password, full_name as fullName from sys_user")
                    .executeAndFetch(SysUser.class);
            System.out.println(sysUsers);
        }
    }

    @Test
    public void testReturnPojoFirst() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            SysUser sysUser = connection.createQuery("select user_id as userId, username, password, full_name as fullName from sys_user")
                    .executeAndFetchFirst(SysUser.class);
            System.out.println(sysUser);
        }
    }

    /*
    返回Scalar
     */
    @Test
    public void testReturnObject() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            Object username = connection.createQuery("select username from sys_user order by user_id desc").executeScalar();
            System.out.println(username);
        }
    }


    @Test
    public void testReturnScalar() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            Long count = connection.createQuery("select count(*) from sys_user").executeScalar(Long.class);
            System.out.println(count);
        }
    }

    @Test
    public void testReturnUsername() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            String username = connection.createQuery("select username from sys_user order by user_id desc").executeScalar(String.class);
            System.out.println(username);
        }
    }

    @Test
    public void testReturnUsernameList() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            List<String> usernameList = connection.createQuery("select username from sys_user order by user_id desc").executeScalarList(String.class);
            System.out.println(usernameList);
        }
    }

    @Test
    public void testReturnUsernameCustom() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            String usernmae = connection.createQuery("select username from sys_user order by user_id desc").executeScalar(new Converter<String>() {
                @Override
                public String convert(Object val) throws ConverterException {
                    return "[" + (String) val + "]";
                }

                @Override
                public Object toDatabaseParam(String val) {
                    return val;
                }
            });
            System.out.println(usernmae);
        }
    }

    /*
    返回Map的集合
     */
    @Test
    public void testReturnMap() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            List<Map<String, Object>> result = connection.createQuery("select * from sys_user").executeAndFetchTable().asList();
            System.out.println(result.get(0).get("username"));
        }
    }

    /*
    返回Table
     */
    @Test
    public void testReturnTable() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            Table table = connection.createQuery("select * from sys_user").executeAndFetchTable();
            System.out.println(table.getName());
            System.out.println(table.rows());
            System.out.println(table.columns());
        }
    }

    /*
    懒加载
     */
    @Test
    public void testLazy() {
        Sql2o sql2o = new Sql2o(dataSource);
        final String sql = "select user_id as userId, username, password, full_name as fullName from sys_user";
        final int BATCH_SIZE = 5;
        List<SysUser> batch = new ArrayList<SysUser>(BATCH_SIZE);
        try (Connection connection = sql2o.open()) {
            try (ResultSetIterable<SysUser> sysUsers = connection.createQuery(sql)
                    .executeAndFetchLazy(SysUser.class)) {
                for (SysUser sysUser : sysUsers) {
                    System.out.println(batch.size());
                    if (batch.size() == BATCH_SIZE) {
                        // 批量处理数据

                        batch.clear();
                    }
                    batch.add(sysUser);
                }
            }
        }
    }

    /*
    自定义结果映射
     */
    @Test
    public void testMapping() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            List<SysUser> sysUsers = connection.createQuery("select user_id, username, password, full_name from sys_user")
                    .addColumnMapping("user_id", "userId")
                    .addColumnMapping("full_name", "fullName")
                    .executeAndFetch(SysUser.class);
            System.out.println(sysUsers);
        }
    }

    /*
    查询参数
     */
    @Test
    public void testParameter() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            List<SysUser> sysUsers = connection.createQueryWithParams("select user_id, username, password, full_name from sys_user where username = :p1", "root")
                    .addColumnMapping("user_id", "userId")
                    .addColumnMapping("full_name", "fullName")
                    .executeAndFetch(SysUser.class);
            System.out.println(sysUsers);
        }
    }

    @Test
    public void testNameParameter() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            List<SysUser> sysUsers = connection.createQuery("select user_id, username, password, full_name from sys_user where username = :username")
                    .addColumnMapping("user_id", "userId")
                    .addColumnMapping("full_name", "fullName")
                    .addParameter("username", "root")
                    .executeAndFetch(SysUser.class);
            System.out.println(sysUsers);
        }
    }

    /*
    更新
     */
    @Test
    public void testUpdate() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            int result = connection.createQuery("update sys_user set full_name = :fullName where user_id = :userId")
                    .addParameter("fullName", "Edgar")
                    .addParameter("userId", 2454)
                    .executeUpdate().getResult();
            System.out.println(result);
        }
    }

    /*
    删除
     */
    @Test
    public void testDelete() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            int result = connection.createQuery("delete from sys_user where user_id = :userId")
                    .addParameter("userId", 2454)
                    .executeUpdate().getResult();
            System.out.println(result);
        }
    }

    /*
    插入
     */
    @Test
    public void testInsert() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            int result = connection.createQuery("insert into sys_user(user_id, username, password, full_name) values(:userId, :username, :password, :fullName)")
                    .addParameter("userId", 2454)
                    .addParameter("username", 2454)
                    .addParameter("password", 2454)
                    .addParameter("fullName", 2454)
                    .executeUpdate().getResult();
            System.out.println(result);
        }
    }

    /*
    返回自动生成的主键
     */
    @Test
    public void testInsertThenGetKey() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            Object result = connection.createQuery("insert into test(name) values(:name)")
                    .addParameter("name", "Test")
                    .executeUpdate().getKey();
            System.out.println(result);
        }
    }

        /*
    插入POJO
     */
    @Test
    public void testInsertPojo() {
        Sql2o sql2o = new Sql2o(dataSource);
        SysUser sysUser = new SysUser();
        sysUser.setUserId(9999);
        sysUser.setUsername("9999");
        sysUser.setPassword("9999");
        sysUser.setFullName("9999");
        try (Connection connection = sql2o.open()) {
            int result = connection.createQuery("insert into sys_user(user_id, username, password, full_name) values(:userId, :username, :password, :fullName)")
                    .bind(sysUser)
                    .executeUpdate().getResult();
            System.out.println(result);
        }
    }

    /*
    事务
     */
    @Test
    public void testTransaction() {
        String sql = "insert into sys_user(user_id, username, password, full_name) values(:userId, :username, :password, :fullName)";
        Sql2o sql2o = new Sql2o(dataSource);
        SysUser sysUser = new SysUser();
        sysUser.setUserId(9999);
        sysUser.setUsername("9999");
        sysUser.setPassword("9999");
        sysUser.setFullName("9999");
        try (Connection connection = sql2o.beginTransaction()) {
             connection.createQuery(sql)
                    .bind(sysUser)
                    .executeUpdate().getResult();
            connection.createQuery(sql)
                    .bind(sysUser)
                    .executeUpdate().getResult();
            connection.commit();
        }
    }

    /*
    批量插入
     */

    @Test
    public void testInsertBatch() {
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.beginTransaction()) {
            Query query = connection.createQuery("insert into test(name) values(:name)");
            for (int i = 0; i < 100; i ++) {
                query.addParameter("name", "test" + i).addToBatch();
            }
            int [] result = query.executeBatch().getBatchResult();
            System.out.println(result.length);
        }
    }
}
