package com.edgar.core.repository;

import com.edgar.core.repository.transaction.Transaction;
import com.edgar.core.repository.transaction.TransactionConfig;
import com.edgar.core.repository.transaction.TransactionFactory;
import com.edgar.module.sys.repository.domain.TestTable;
import com.edgar.module.sys.repository.querydsl.QTestTable;
import com.mysema.query.sql.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/8/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class InsertTransactionTest {

    @Autowired
    private DataSource dataSource;

    private Configuration configuration = ConfigurationFactory.createConfiguration(Constants.DEFAULT);


    private TransactionConfig config;

    private RowMapper<TestTable> rowMapper = BeanPropertyRowMapper.newInstance(TestTable.class);

    @Before
    public void setUp() {
        config = new TransactionConfig(dataSource, configuration, QTestTable.testTable);
    }

    @Transactional
    @Test
    public void testInsert() {
        QueryExample example = QueryExample.newInstance();
        example = QueryExample.newInstance();
        Transaction transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        List<TestTable> testTables = transaction.execute();
        Assert.assertEquals(0, testTables.size());

        testTables = new ArrayList<TestTable>();
        for (int i = 0; i < 10; i++) {
            TestTable testTable = new TestTable();
            testTable.setTestCode("000" + i);
            testTable.setDictName("000" + i);
            testTable.setParentCode("-1");
            testTable.setSorted(9999);
            transaction = TransactionFactory.createDefaultInsertTransaction(config, testTable);
            Long result = transaction.execute();
            Assert.assertEquals(1l, result, 0);
        }

        transaction = TransactionFactory.createQueryTransaction(config, example, rowMapper);
        testTables = transaction.execute();
        Assert.assertEquals(10, testTables.size());
    }
}
