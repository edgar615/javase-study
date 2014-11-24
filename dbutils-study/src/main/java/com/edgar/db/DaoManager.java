package com.edgar.db;

import com.edgar.core.util.ExceptionUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 同一个线程、同一个数据源使用相同的Connection。不同的线程使用不用的Connection
 */
public class DaoManager {

    private static final DaoManager INSTANCE = new DaoManager();
    private static final DataSource dataSource = new DataSource();;
    private static final Properties sqlProperties = new Properties();

    private final ThreadLocal<Connection> connections = new ThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                return null;
            }
        }

        @Override
        protected void finalize() throws Throwable {
            try {
                System.out.println("finalize2");
            } finally {
                super.finalize();
            }
        }
    };

    private DaoManager() {

    }

    public static DaoManager getInstance() {
        return INSTANCE;
    }

    public void init(String dataSourceFile, String sqlFile) {
        initDataSource(dataSourceFile);
        initSql(sqlFile);
    }

    public Connection getConnection() {
        return connections.get();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Properties getSqlProperties() {
        return sqlProperties;
    }

    public void close() {
        Connection conn = getConnection();
        try {
            if (conn != null && !conn.isClosed()) {
                DbUtils.close(conn);
            }
        } catch (SQLException e) {
            ExceptionUtils.thrwoAppException(this.getClass().getName() + ".executeAndClose", "SQL", "数据库操作异常", e);
        }
        connections.remove();
    }

    public <T> T execute(SqlExecutor<T> executor) {
        Connection conn = null;
        T result = null;
        try {
            conn = getConnection();
            result = executor.doInConnection(conn);
        } catch (SQLException e) {
            ExceptionUtils.thrwoAppException(this.getClass().getName() + ".execute", "SQL", "数据库操作异常", e);
        }
        return result;
    }

    public <T> T executeAndClose(SqlExecutor<T> executor) {
        Connection conn = null;
        T result = null;
        try {
            conn = getConnection();
            result = execute(executor);
        } finally {
            try {
                DbUtils.close(conn);
            } catch (SQLException e) {
                ExceptionUtils.thrwoAppException(this.getClass().getName() + ".executeAndClose", "SQL", "数据库操作异常", e);
            }
        }
        return result;
    }

    public <T> T executeInTransaction(SqlExecutor<T> executor) {
        Connection conn = getConnection();
        T result = null;
        try {
            conn.setAutoCommit(false);
            conn.setReadOnly(false);
            result = executor.doInConnection(conn);
            return result;
        } catch (Exception e) {
            try {
                DbUtils.rollback(conn);
            } catch (SQLException e1) {
                ExceptionUtils.thrwoAppException(this.getClass().getName() + ".executeInTransaction", "SQL", "数据库操作异常", e);
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                ExceptionUtils.thrwoAppException(this.getClass().getName() + ".executeInTransaction", "SQL", "数据库操作异常", e);
            }
        }
        return result;
    }

    public <T> T executeInTransactionAndClose(final SqlExecutor<T> executor) {
        return executeAndClose(new SqlExecutor<T>() {
            @Override
            public T doInConnection(Connection conn) throws SQLException {
                return executeInTransaction(executor);
            }
        });
    }

    private void initSql(String sqlFile) {
        sqlProperties.putAll(getPropertiesFromClasspath(sqlFile));
    }

    private void initDataSource(String dataSourceFile) {
        Properties dataSourceProps = getPropertiesFromClasspath(dataSourceFile);
        PoolProperties p = new PoolProperties();
        p.setUrl(dataSourceProps.getProperty("url"));
        p.setDriverClassName(dataSourceProps.getProperty("driverClassName"));
        p.setUsername(dataSourceProps.getProperty("username"));
        p.setPassword(dataSourceProps.getProperty("password"));
        p.setMaxIdle(Integer.valueOf(dataSourceProps.getProperty("maxIdle")));
        p.setMinIdle(Integer.valueOf(dataSourceProps.getProperty("minIdle")));
        p.setInitialSize(Integer.valueOf(dataSourceProps.getProperty("initialSize")));
        p.setMaxWait(Integer.valueOf(dataSourceProps.getProperty("maxWait")));
        p.setMaxActive(Integer.valueOf(dataSourceProps.getProperty("maxActive")));
        p.setTestOnBorrow(Boolean.valueOf(dataSourceProps.getProperty("testOnBorrow")));
        p.setTestOnReturn(Boolean.valueOf(dataSourceProps.getProperty("testOnReturn")));
        p.setTestWhileIdle(Boolean.valueOf(dataSourceProps.getProperty("testWhileIdle")));
        p.setValidationQuery(dataSourceProps.getProperty("validationQuery"));
        p.setValidationQueryTimeout(Integer.valueOf(dataSourceProps.getProperty("validationQueryTimeout")));
        p.setTimeBetweenEvictionRunsMillis(Integer.valueOf(dataSourceProps.getProperty("timeBetweenEvictionRunsMillis")));
        p.setMinEvictableIdleTimeMillis(Integer.valueOf(dataSourceProps.getProperty("minEvictableIdleTimeMillis")));
        p.setRemoveAbandoned(Boolean.valueOf(dataSourceProps.getProperty("removeAbandoned")));
        p.setRemoveAbandonedTimeout(Integer.valueOf(dataSourceProps.getProperty("removeAbandonedTimeout")));
        p.setValidationInterval(Integer.valueOf(dataSourceProps.getProperty("validationInterval")));
        p.setLogAbandoned(Boolean.valueOf(dataSourceProps.getProperty("logAbandoned")));
        p.setFairQueue(Boolean.valueOf(dataSourceProps.getProperty("fairQueue")));
        dataSource.setPoolProperties(p);
    }

    private static Properties getPropertiesFromClasspath(String fileName) {

        Properties props = new Properties();
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream(fileName);
            if (is == null) { // try this instead
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            }
            props.load(is);
        } catch (Exception e) {
            ExceptionUtils.thrwoAppException("getPropertiesFromClasspath", "FILE_NOT_FOUND", "读取" + fileName + "失败", e);
        }
        return props;
    }


}
