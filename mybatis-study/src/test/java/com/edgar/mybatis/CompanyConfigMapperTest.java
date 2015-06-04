package com.edgar.mybatis;

import com.edgar.domain.CompanyConfig;
import com.edgar.mapper.CompanyConfigMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/6/3.
 */
public class CompanyConfigMapperTest {

    SqlSession session;

    @Before
    public void setUp() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
    }

    @After
    public void tearDown() {
        session.close();
    }

    @Test
    public void test() throws IOException {
        CompanyConfigMapper mapper = session.getMapper(CompanyConfigMapper.class);
        CompanyConfig config = mapper.selectByPrimaryKey(1);
        System.out.println(config.getConfigKey());
    }

    @Test
    public void count() throws IOException {
        CompanyConfigMapper mapper = session.getMapper(CompanyConfigMapper.class);
        System.out.println(mapper.count());
    }
}
