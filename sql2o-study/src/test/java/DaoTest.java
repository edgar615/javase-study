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
import org.sql2o.reflection.PojoIntrospector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/17.
 */
public class DaoTest {

    private DataSource dataSource;

    @Before
    public void setUp() {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://127.0.0.1:3308/admin");
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


    /*
    返回自动生成的主键
     */
    @Test
    public void testInsert() {
        String tableName = "sys_user";
        StringBuilder insertString = new StringBuilder("insert into sys_user(");
        StringBuilder valueString = new StringBuilder("values(");

        Class clazz = SysUser.class;
        Map<String, PojoIntrospector.ReadableProperty> propertyMap = PojoIntrospector.readableProperties(clazz);
        for (PojoIntrospector.ReadableProperty property : propertyMap.values()) {
            try {
                final Class<Object> type = (Class<Object>) property.type;
                String propertyName = property.name;
                insertString.append(propertyName).append(",");
                valueString.append(":").append(propertyName).append(",");
//                property.name, type, property.get(pojo);
            }
            catch(IllegalArgumentException ex) {
//                logger.debug("Ignoring Illegal Arguments", ex);
            }
        }

        insertString.replace(insertString.length()-1, insertString.length(), ")");
        valueString.replace(valueString.length() - 1, valueString.length(), ")");
        insertString.append(" ").append(valueString);
        System.out.println(insertString);
                SysUser sysUser = new SysUser();
        sysUser.setUserId(9999);
        sysUser.setUsername("9999");
        sysUser.setPassword("9999");
        sysUser.setFullName("9999");
        Sql2o sql2o = new Sql2o(dataSource);
        try (Connection connection = sql2o.open()) {
            Object result = connection.createQuery(insertString.toString())
                    .bind(sysUser)
                    .executeUpdate().getResult();
            System.out.println(result);
        }
    }

    /*
插入POJO
 */
//    @Test
//    public void testInsertPojo() {
//        Sql2o sql2o = new Sql2o(dataSource);
//        SysUser sysUser = new SysUser();
//        sysUser.setUserId(9999);
//        sysUser.setUsername("9999");
//        sysUser.setPassword("9999");
//        sysUser.setFullName("9999");
//        try (Connection connection = sql2o.open()) {
//            int result = connection.createQuery("insert into sys_user(user_id, username, password, full_name) values(:userId, :username, :password, :fullName)")
//                    .bind(sysUser)
//                    .executeUpdate().getResult();
//            System.out.println(result);
//        }
//    }

}
