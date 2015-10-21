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
public class PageTransactionTest {


    @Autowired
    private CacheProviderFactory cacheProviderFactory;
    @Autowired
    private DataSource dataSource;

    private Configuration configuration = ConfigurationFactory.createConfiguration(Constants.DEFAULT);

    private TransactionConfig config;

    private RowMapper<TestTable> rowMapper = BeanPropertyRowMapper.newInstance(TestTable.class);


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
    public void testPage() {
        QueryExample example = QueryExample.newInstance();

        Transaction transaction = TransactionFactory.createPageTransaction(config, example, 1, 10, rowMapper);
        Pagination<TestTable> testTables = transaction.execute();

        Assert.assertEquals(1, testTables.getPage());
        Assert.assertEquals((testTables.getTotalRecords() + testTables.getPageSize() - 1)
                / testTables.getPageSize(), testTables.getTotalPages());
        Assert.assertEquals(1, testTables.getPrevPage());
        Assert.assertEquals(1, testTables.getNextPage());

        transaction = TransactionFactory.createPageTransaction(config, example, 2, 10, rowMapper);
        testTables = transaction.execute();
        Assert.assertEquals(10, testTables.getRecords().size());
        Assert.assertEquals(1, testTables.getPage());
        Assert.assertEquals((testTables.getTotalRecords() + testTables.getPageSize() - 1)
                / testTables.getPageSize(), testTables.getTotalPages());
        Assert.assertEquals(1, testTables.getPrevPage());
        Assert.assertEquals(1, testTables.getNextPage());

        transaction = TransactionFactory.createPageTransaction(config, example, 3, 5, rowMapper);
        testTables = transaction.execute();
        Assert.assertEquals(2, testTables.getPage());
        Assert.assertEquals((testTables.getTotalRecords() + testTables.getPageSize() - 1)
                / testTables.getPageSize(), testTables.getTotalPages());
        Assert.assertEquals(1, testTables.getPrevPage());
        Assert.assertEquals(2, testTables.getNextPage());
    }

    @Transactional
    @Test(expected = IllegalArgumentException.class)
    public void testGreatThanMaxPage() {
        QueryExample example = QueryExample.newInstance();
        example.setMaxNumOfRecords(1000);

        Transaction transaction = TransactionFactory.createPageTransaction(config, example, 200, 10, rowMapper);
        Pagination<TestTable> testTables = transaction.execute();
    }
}
