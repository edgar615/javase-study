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

/**
 * Created by Administrator on 2014/8/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class DeleteTransactionTest {
    @Autowired
    private BaseDao<String, TestTable> testTableDao;
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
    public void testDeleteByExample() {
        QueryExample example = QueryExample.newInstance();
        example.limit(1);
        List<TestTable> testTables = testTableDao.query(example);
        TestTable testTable = testTables.get(0);

        example.clear();
        example.equalsTo("testCode", testTable.getTestCode());
        example.equalsTo("updatedTime", testTable.getUpdatedTime());

        Transaction transaction = TransactionFactory.createDeleteTransaction(config, example);
        Long result = transaction.execute();
        Assert.assertEquals(1l, result, 0);
        testTable = testTableDao.get(testTable.getTestCode());
        Assert.assertNull(testTable);
    }

    @Transactional
    @Test
    public void testDeleteByExampleNotEquals() {

        QueryExample example = QueryExample.newInstance();
        example.notEqualsTo("testCode", "0001");
        Transaction transaction = TransactionFactory.createDeleteTransaction(config, example);
        Long result = transaction.execute();
        Assert.assertEquals(9l, result, 0);
    }
}
