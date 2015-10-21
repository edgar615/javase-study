package com.edgar.core.helper;

import com.edgar.core.util.ServiceLookup;
import com.edgar.module.sys.dao.SysDictDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class ServiceLookupTest {

    @Test
    public void testGetBean() {
        SysDictDao sysDictDao = ServiceLookup.getBean(SysDictDao.class);
        Assert.assertNotNull(sysDictDao);
    }

    @Test
    public void testGetBean2() {
        SysDictDao sysDictDao = ServiceLookup.getBean("sysDictDao", SysDictDao.class);
        Assert.assertNotNull(sysDictDao);
    }
}
