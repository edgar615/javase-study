package com.edgar.core.validator;

import com.edgar.core.exception.SystemException;
import com.edgar.module.sys.repository.domain.SysUser;
import com.edgar.module.sys.validator.SysUserUpdateValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class ValidatorTest {

    @Autowired
    private BusinessService businessService;

    @Test
    public void testValid() {
        try {
            System.out.println(businessService.convertToUpperCase("12345"));
        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                System.out.println("Method Validation: {}" + violation.getMessage());
            }
        }
    }

    @Test
    public void testRegex() {
        Pattern pattern = Pattern.compile("(/:?[0-9a-zA-Z_]+)+");
        Matcher matcher = pattern.matcher("/123ddd/zz/zz_/");
        boolean b = matcher.matches();
        //当条件满足时，将返回true，否则返回false
        System.out.println(b);
    }

    @Test
    public void testUser() {
        SysUser sysUser = new SysUser();
        sysUser.setPassword("");
        SysUserUpdateValidator validator = new SysUserUpdateValidator();
        try {
            validator.validator(sysUser);
        } catch (SystemException e) {
            System.out.println(e.getPropertyMap());
        }
    }

    @Test
    public void testRegex2() {
        Pattern pattern = Pattern.compile("^(?!root$).+$");
        Assert.assertTrue(pattern.matcher("root2").matches());
        Assert.assertFalse(pattern.matcher("root").matches());
        Assert.assertTrue(pattern.matcher("1root").matches());
    }

}
