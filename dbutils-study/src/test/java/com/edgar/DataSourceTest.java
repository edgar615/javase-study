package com.edgar;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * http://www.tomcatexpert.com/blog/2010/04/19/building-extensions-jdbc-pool
 * http://www.tomcatexpert.com/blog/2010/04/01/configuring-jdbc-pool-high-concurrency
 * http://duzc2.iteye.com/blog/1538008
 */
public class DataSourceTest {

    private DataSource dataSource;

    @Before
    public void setUp() {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/mysql");
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
    public void testDataSource() throws SQLException {


        Connection con = null;
        try {
            con = dataSource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from user");
            int cnt = 1;
            while (rs.next()) {
                System.out.println((cnt++) + ". Host:" + rs.getString("Host") +
                        " User:" + rs.getString("User") + " Password:" + rs.getString("Password"));
            }
            rs.close();
            st.close();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
    }

    /**
     *  The Tomcat JDBC connection pool supports asynchronous connection retrieval without adding additional threads to the pool library.
     *  It does this by adding a method to the data source called Future<Connection> getConnectionAsync().
     *  In order to use the async retrieval, two conditions must be met:

     You must configure the fairQueue property to be true.
     You will have to cast the data source to org.apache.tomcat.jdbc.pool.DataSource

     * @throws SQLException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testAsynchronous() throws SQLException, ExecutionException, InterruptedException {
        Connection connection = null;
        try {
            Future<Connection> future = dataSource.getConnectionAsync();
            while (!future.isDone()) {
                System.out.println("Connection is not yet available. Do some background work");
                try {
                    Thread.sleep(100); //simulate work
                } catch (InterruptedException x) {
                    Thread.currentThread().interrupt();
                }
            }
            connection = future.get(); //should return instantly
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from user");
            int cnt = 1;
            while (rs.next()) {
                System.out.println((cnt++) + ". Host:" + rs.getString("Host") +
                        " User:" + rs.getString("User") + " Password:" + rs.getString("Password"));
            }
            rs.close();
            st.close();

        } finally {
            if (connection != null) try {
                connection.close();
            } catch (Exception ignore) {
            }
        }
    }
}
