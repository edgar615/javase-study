import com.edgar.core.jdbc.JdbcRepository;
import com.generated.code.domain.CompanyConfig;
import com.generated.code.repository.CompanyConfigRepository;
import com.generated.code.repository.db.CompanyConfigDB;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edgar on 14-11-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dao.xml"})
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class JdbcRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Sql2o sql2o;

    private JdbcTemplate jdbcTemplate;

    private JdbcRepository<CompanyConfig, Integer> jdbcRepository;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "create table `company_config` (\n" +
                "  `config_id` int(11) not null,\n" +
                "  `company_id` int(11) default null,\n" +
                "  `config_key` varchar(16) not null,\n" +
                "  `config_value` varchar(16) not null,\n" +
                "  `description` varchar(128) default null,\n" +
                "  primary key (`config_id`)\n" +
                ") engine=innodb default charset=utf8";
        jdbcTemplate.execute(sql);

        jdbcRepository = new CompanyConfigRepository();
        jdbcRepository.setDataSource(dataSource);

        for (int i = 0; i < 10; i++) {
            CompanyConfig companyConfig = new CompanyConfig();
            companyConfig.setConfigId(i);
            companyConfig.setCompanyId(i);
            companyConfig.setConfigKey("key" + i);
            companyConfig.setConfigValue("value" + i);
            jdbcRepository.insert(CompanyConfigDB.NAMED_INSERT_SQL, companyConfig);

        }

    }

//    @After
//    public void tearDown() {
//        String sql = "drop table company_config";
//        jdbcTemplate.execute(sql);
//    }

    @Test
    public void testInsert() {
        int count = count();

        CompanyConfig companyConfig = new CompanyConfig();
        companyConfig.setConfigId(11);
        companyConfig.setCompanyId(11);
        companyConfig.setConfigKey("key");
        companyConfig.setConfigValue("value");
        int result = jdbcRepository.insert(CompanyConfigDB.NAMED_INSERT_SQL, companyConfig);
        Assert.assertEquals(1, result);
        Assert.assertEquals(count + 1, count());
    }

    @Test
    public void testUpdateByPrimaryKey() {

        CompanyConfig companyConfig = new CompanyConfig();
        companyConfig.setConfigId(11);
        companyConfig.setCompanyId(11);
        companyConfig.setConfigKey("key");
        companyConfig.setConfigValue("value");
        int result = jdbcRepository.updateByPrimaryKey(CompanyConfigDB.NAMED_UPDATE_BY_PK_SQL, companyConfig);
        Assert.assertEquals(0, result);

        companyConfig = new CompanyConfig();
        companyConfig.setConfigId(1);
        companyConfig.setCompanyId(1);
        companyConfig.setConfigKey("New Key");
        companyConfig.setConfigValue("New Value");
        result = jdbcRepository.updateByPrimaryKey(CompanyConfigDB.NAMED_UPDATE_BY_PK_SQL, companyConfig);
        Assert.assertEquals(1, result);

        companyConfig = jdbcTemplate.queryForObject("select * from company_config where config_id = 1", CompanyConfigDB.ROW_MAPPER);
        Assert.assertEquals("New Key", companyConfig.getConfigKey());
    }

    @Test
    public void testDeleteByPrimaryKey() {
        int count = count();
        int result = jdbcRepository.deleteByPrimaryKey(11);
        Assert.assertEquals(0, result);
        Assert.assertEquals(count, count());

        result = jdbcRepository.deleteByPrimaryKey(1);
        Assert.assertEquals(1, result);
        Assert.assertEquals(count - 1, count());

    }

//    @Test
//    public void testDeleteByPk() {
//        int count = count();
//
//        int result = jdbcRepository.delete(CompanyConfigDB.INSERT_SQL, CompanyConfigDB.ROW_UNMAPPER.mapColumns(companyConfig));
//        Assert.assertEquals(1, result);
//        Assert.assertEquals(count + 1 , count());
//    }

    private int count() {
        String sql = "select count(*) from company_config";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

}
