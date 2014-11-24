import com.edgar.core.exception.AppException;
import com.edgar.core.util.ExceptionUtils;
import com.edgar.db.DaoManager;
import com.edgar.db.SqlExecutor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * DaoManager的测试类
 * @author Edgar
 * @version 1.0
 */
public class DaoManagerTest {

    @Before
    public void setUp() {
        DaoManager.getInstance().init("sql" + File.separator + "datasource.properties", "sql" + File.separator + "sql.properties");
        DaoManager.getInstance().close();
    }

    /**
     * 测试同一个线程里的Connection相同
     * @throws java.sql.SQLException
     */
    @Test
    public void testConnectionEqualsInOneThread() throws SQLException {
        DaoManager daoManager1 = DaoManager.getInstance();
        DaoManager daoManager2 = DaoManager.getInstance();
        daoManager1.getConnection();
        daoManager2.getConnection();
        Assert.assertTrue(daoManager1 == daoManager2);
        Assert.assertTrue(daoManager1.getConnection() == daoManager2.getConnection());
    }

    /**
     * 测试不同的线程里的Connection不同
     * @throws java.sql.SQLException
     * @throws InterruptedException
     */
    @Test
    public void testConnectionDiffInTwoThread() throws SQLException, InterruptedException {
        final List<DaoManager> daoManagers = new ArrayList<DaoManager>();
        final List<Connection> connections = new ArrayList<Connection>();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                DaoManager daoManager1 = DaoManager.getInstance();
                daoManagers.add(daoManager1);
                Connection connection = daoManager1.getConnection();
                connections.add(connection);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                DaoManager daoManager2 = DaoManager.getInstance();
                daoManagers.add(daoManager2);
                Connection connection = daoManager2.getConnection();
                connections.add(connection);
            }
        });

        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(5);
        Assert.assertTrue(daoManagers.get(0) == daoManagers.get(1));
        Assert.assertFalse(connections.get(0) == connections.get(1));
    }

    @Test
    public void testExecuteThenOpenConn() throws SQLException {
        final String sql = "select * from sys_user where username = ?";
        List<String> result = DaoManager.getInstance().execute(new SqlExecutor<List<String>>() {
            @Override
            public List<String> doInConnection(Connection conn) throws SQLException {
                QueryRunner runner = new QueryRunner();
                return runner.query(conn, sql, new ColumnListHandler<String>("username"), "root");
            }
        });
        Connection connection = DaoManager.getInstance().getConnection();
        Assert.assertFalse(connection.isClosed());
    }

    @Test
    public void testExecuteThenCloseConn() throws SQLException {
        final String sql = "select * from sys_user where username = ?";
        List<String> result = DaoManager.getInstance().executeAndClose(new SqlExecutor<List<String>>() {
            @Override
            public List<String> doInConnection(Connection conn) throws SQLException {
                QueryRunner runner = new QueryRunner();
                return runner.query(conn, sql, new ColumnListHandler<String>("username"), "root");
            }
        });
        Connection connection = DaoManager.getInstance().getConnection();
        Assert.assertTrue(connection.isClosed());
    }

    @Test
    public void testExecuteInTransaction() throws SQLException {
        final String insertSql = "insert into sys_user(user_id, username, password, full_name) values (?, ?, ?, ?)";
        Long result = count();
        try {
            DaoManager.getInstance().executeInTransaction(new SqlExecutor<Object>() {
                @Override
                public Object doInConnection(Connection conn) throws SQLException {
                    QueryRunner runner = new QueryRunner();
                    long count = runner.update(conn, insertSql, 1, "zyz", "zyz", "zyz");
                    throw new RuntimeException("error");
                }
            });
        } catch (Exception e) {
        }
        Long result2 = count();
        Assert.assertEquals(result, result2, 0);

        Connection connection = DaoManager.getInstance().getConnection();
        Assert.assertFalse(connection.isClosed());
    }

    @Test(expected = AppException.class)
    public void testExecuteInTransactionAndClose() throws SQLException {
        final String insertSql = "insert into sys_user(user_id, username, password, full_name) values (?, ?, ?, ?)";
        Long result = count();
        try {
            DaoManager.getInstance().executeInTransactionAndClose(new SqlExecutor<Object>() {
                @Override
                public Object doInConnection(Connection conn) throws SQLException {
                    QueryRunner runner = new QueryRunner();
                    long count = runner.update(conn, insertSql, 1, "zyz", "zyz", "zyz");
                    throw new RuntimeException("error");
                }
            });
        } catch (Exception e) {
        }
        Long result2 = count();
        Assert.assertEquals(result, result2, 0);

        Connection connection = DaoManager.getInstance().getConnection();
        Assert.assertTrue(connection.isClosed());
    }

    private long count() {
        final String sql = "select count(*) from sys_user";
        return DaoManager.getInstance().execute(new SqlExecutor<Long>() {
            @Override
            public Long doInConnection(Connection conn) throws SQLException {
                QueryRunner runner = new QueryRunner();
                return runner.query(conn, sql, new ScalarHandler<Long>(1));
            }
        });
    }
}
