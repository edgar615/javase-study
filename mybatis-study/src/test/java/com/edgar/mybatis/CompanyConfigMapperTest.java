package com.edgar.mybatis;

import com.edgar.domain.CompanyConfig;
import com.edgar.mapper.CompanyConfigMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/6/3.
 */
public class CompanyConfigMapperTest {

    SqlSession session;
    CompanyConfigMapper mapper;

    @Before
    public void setUp() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
        mapper = session.getMapper(CompanyConfigMapper.class);

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
        session.close();
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

}
