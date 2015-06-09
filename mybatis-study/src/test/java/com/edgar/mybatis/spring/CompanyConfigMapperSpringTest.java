package com.edgar.mybatis.spring;

import com.edgar.core.jdbc.Pagination;
import com.edgar.core.repository.PaginationHelper;
import com.edgar.domain.CompanyConfig;
import com.edgar.repository.CompanyConfigMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dao.xml"})
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class CompanyConfigMapperSpringTest {

    @Autowired
    CompanyConfigMapper mapper;

    @Before
    public void setUp() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        mapper.createTable();

        for (int i = 0; i < 10; i++) {
            CompanyConfig companyConfig = new CompanyConfig();
            companyConfig.setConfigId(i);
            companyConfig.setCompanyId(i);
            companyConfig.setConfigKey("key" + i);
            companyConfig.setConfigValue("value" + i);
            mapper.insert(companyConfig);

        }
    }

    @After
    public void tearDown() {
        mapper.dropTable();
    }

    @Test
    public void testSelectByPrimaryKey() throws IOException {
        CompanyConfig config = mapper.selectByPrimaryKey(1);
        Assert.assertEquals("key1", config.getConfigKey());
        config = mapper.selectByPrimaryKey(20);
        Assert.assertNull(config);
    }

    @Test
    public void testInsert() throws IOException {
        int count = mapper.count();
        CompanyConfig companyConfig = new CompanyConfig();
        companyConfig.setConfigId(11);
        companyConfig.setCompanyId(11);
        companyConfig.setConfigKey("key" + 11);
        companyConfig.setConfigValue("value" + 11);
        int result = mapper.insert(companyConfig);
        Assert.assertEquals(1, result);
        Assert.assertEquals(count + 1, mapper.count());
    }

    @Test
    public void testUpdateByPrimaryKey() throws IOException {
        CompanyConfig config = mapper.selectByPrimaryKey(1);
        Assert.assertEquals("key1", config.getConfigKey());
        config.setConfigKey("new key");
        config.setConfigValue("new value");
        int result = mapper.updateByPrimaryKey(config);
        Assert.assertEquals(1, result);
        config = mapper.selectByPrimaryKey(1);
        Assert.assertEquals("new key", config.getConfigKey());
        config.setConfigId(200);
        result = mapper.updateByPrimaryKey(config);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testDeleteByPrimaryKey() throws IOException {
        int count = mapper.count();
        int result = mapper.deleteByPrimaryKey(1);
        Assert.assertEquals(1, result);
        Assert.assertEquals(count - 1, mapper.count());
        result = mapper.deleteByPrimaryKey(200);
        Assert.assertEquals(0, result);
        Assert.assertEquals(count - 1, mapper.count());
    }

    @Test
    public void testDynamicCount() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("configValue", "value1");
//        map.put("configKey", "0001");
        int count = mapper.count(map);
        map.put("configKey", "key14");
        count = mapper.count(map);
        Assert.assertEquals(0, count);
    }

    @Test
    public void testDynamicQuery() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("configValue", "value1");
//        map.put("configKey", "0001");
        List<CompanyConfig> companyConfigs = mapper.query(map);
        Assert.assertEquals(1, companyConfigs.size());
        map.put("configKey", "key14");
        companyConfigs = mapper.query(map);
        Assert.assertEquals(0, companyConfigs.size());
    }

    @Test
    public void testPagination() {
        Map<String, Object> map = new HashMap<>();
//        map.put("configValue", "value1");
        Pagination<CompanyConfig> pagination = PaginationHelper.fetchPage(mapper, map, 1, 3);

        Assert.assertEquals(1, pagination.getPage());
        Assert.assertEquals(3, pagination.getPageSize());
        Assert.assertEquals(4, pagination.getPageList().size());
        Assert.assertEquals(4, pagination.getTotalPages());
        Assert.assertEquals(10, pagination.getTotalRecords());
        Assert.assertEquals(3, pagination.getRecords().size());
    }

}
