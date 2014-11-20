import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.util.Random;

/**
 * Created by Edgar on 14-11-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dao.xml"})
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class Sql2oSpringTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Sql2o sql2o;

    @Transactional
    @Test
    public void test() {
        int count = count();
        try (Connection connection = sql2o.open()) {
            int result = connection.createQuery("insert into sys_user(user_id, username, password, full_name) values(:userId, :username, :password, :fullName)")
                    .addParameter("userId", new Random().nextInt(100000))
                    .addParameter("username", "TEST")
                    .addParameter("password", "TEST")
                    .addParameter("fullName", "TEST")
                    .executeUpdate().getResult();
            System.out.println(result);
            throw new RuntimeException("error");
        }
//        Assert.assertEquals(count + 1, count());
    }

    private int count() {
        try (Connection connection = sql2o.open()) {
            int count = connection.createQuery("select count(*) from sys_user").executeScalar(Integer.class);
            return count;
        }
    }
}
