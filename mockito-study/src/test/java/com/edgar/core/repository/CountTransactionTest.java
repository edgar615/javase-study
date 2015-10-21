package com.edgar.core.repository;

import com.edgar.core.cache.CacheProviderFactory;
import com.edgar.core.repository.transaction.Transaction;
import com.edgar.core.repository.transaction.TransactionConfig;
import com.edgar.core.repository.transaction.TransactionFactory;
import com.edgar.module.sys.repository.domain.TestTable;
import com.edgar.module.sys.repository.querydsl.QTestTable;
import com.mysema.query.sql.Configuration;
import org.junit.After;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class CountTransactionTest {

    @Autowired
    private DataSource dataSource;

    private Configuration configuration = ConfigurationFactory.createConfiguration(Constants.DEFAULT);

    private TransactionConfig config;

    private RowMapper<TestTable> rowMapper = BeanPropertyRowMapper.newInstance(TestTable.class);


    @Autowired
    private CacheProviderFactory cacheProviderFactory;

    @Before
    public void setUp() {
        config = new TransactionConfig(dataSource, configuration, QTestTable.testTable);
        List<TestTable> testTables = new ArrayList<TestTable>();
        for (int i = 0; i < 10; i++) {
            TestTable testTable = new TestTable();
            testTable.setTestCode("000" + i);
            testTable.setDictName("000" + i);
            testTable.setParentCode("-1");
            testTable.setSorted(9999);
            testTables.add(testTable);
        }

        Transaction transaction = TransactionFactory.createDefaultBatchInsertTransaction(config, testTables);
        transaction.execute();
    }

    @After
    public void tearDown() {
        cacheProviderFactory.createCacheWrapper("TestTableCache").removeAll();
        cacheProviderFactory.createCacheWrapper("Test2TableCache").removeAll();
    }

    @Transactional
    @Test
    public void testQuery() {
        QueryExample example = QueryExample.newInstance();
        example = QueryExample.newInstance();
        Transaction transaction = TransactionFactory.createCountTransaction(config, example);
        Long count = transaction.execute();
        Assert.assertEquals(10, count, 0);

        example.limit(5);
        transaction = TransactionFactory.createCountTransaction(config, example);
        count = transaction.execute();
        Assert.assertEquals(5, count, 0);
        example.equalsTo("testCode", "0001");
        transaction = TransactionFactory.createCountTransaction(config, example);
        count = transaction.execute();
        Assert.assertEquals(1, count, 0);

        example.clear();
        example.limit(10);
        example.greaterThan("test_code", "0001");
        example.asc("sorted");
        example.desc("testCode");
        transaction = TransactionFactory.createCountTransaction(config, example);
        count = transaction.execute();
        Assert.assertEquals(8, count, 0);
    }


}
