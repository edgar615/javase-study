package com.edgar.core.mail;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class MaiServiceTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private VelocityEngine engine;

    @Test
    public void testSimpleMail() {
        mailService.sendSimpleMail("cdmserver@126.com", "yuzhou.zhang@csst.com", "测试邮件",
                "这是一封测试邮件");
    }

    @Test
    public void testSimpleMailTo2() {
        List<String> to = new ArrayList<String>();
        to.add("yuzhou.zhang@csst.com");
        to.add("edgar615@gmail.com");

        mailService.sendSimpleMail("cdmserver@126.com", to, "测试邮件", "这是一封测试邮件");
    }

    @Test
    public void testSimpleMailCc() {
        List<String> to = new ArrayList<String>();
        to.add("yuzhou.zhang@csst.com");
        List<String> cc = new ArrayList<String>();
        cc.add("edgar615@gmail.com");

        mailService.sendSimpleMail("cdmserver@126.com", to, cc, "测试邮件", "这是一封测试邮件");
    }

    @Test
    public void testVelocity() {
        List<String> to = new ArrayList<String>();
        to.add("yuzhou.zhang@csst.com");
        List<String> cc = new ArrayList<String>();
        cc.add("edgar615@gmail.com");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("emailAddress", "yuzhou.zhang@csst.com");
        mailService.sendTplEmail(
                "cdmserver@126.com",
                to,
                cc,
                "测试邮件",
                "<html><body><h3>Hi ${user.userName}, welcome to the Chipping Sodbury On-the-Hill message boards!</h3><div>Your email address is <a href='mailto:${emailAddress}'>${emailAddress}</a></div><img src='http://localhost:8080/myapp/assets/img/bg.jpg' alt='测试邮件'/></body></html>",
                model);
    }

    @Test
    public void testVelocityLocation() {
        List<String> to = new ArrayList<String>();
        to.add("yuzhou.zhang@csst.com");
        List<String> cc = new ArrayList<String>();
        cc.add("edgar615@gmail.com");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("emailAddress", "yuzhou.zhang@csst.com");
        mailService.sendTplLocationEmail("cdmserver@126.com", to, cc, "测试邮件", "tpl/500.vm",
                model);
    }
}
