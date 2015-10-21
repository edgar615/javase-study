package com.edgar.module.sys.validator;

import com.edgar.core.exception.SystemException;
import com.edgar.core.validator.ValidatorStrategy;
import com.edgar.module.sys.repository.domain.SysDict;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 14-9-11.
 */
public class SysDictValidatorTest {

    private ValidatorStrategy validator;

    @Before
    public void setUp() {
        validator = new SysDictValidator();
    }

    @Test(expected = SystemException.class)
    public void testSaveCodeTooLang() {
        final SysDict sysDict = new SysDict();
        sysDict.setDictCode("012345678901234567890123456789123");
        validator.validator(sysDict);
    }

    @Test(expected = SystemException.class)
    public void testSaveNullName() {
        final SysDict sysDict = new SysDict();
        sysDict.setDictCode("1");
        validator.validator(sysDict);
    }

    @Test(expected = SystemException.class)
    public void testSaveNameTooLong() {
        final SysDict sysDict = new SysDict();
        sysDict.setDictCode("1");
        sysDict.setDictName("012345678901234567890123456789123");
        validator.validator(sysDict);
    }

    @Test(expected = SystemException.class)
    public void testSaveCodeInvalid() {
        final SysDict sysDict = new SysDict();
        sysDict.setDictCode("1!@#$");
        sysDict.setDictName("012345678901234");
        validator.validator(sysDict);
    }

    @Test
    public void testSaveSuccess() {
        final SysDict sysDict = new SysDict();
        sysDict.setDictCode("1");
        sysDict.setDictName("1");
        validator.validator(sysDict);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveNull() {
        final SysDict sysDict = null;
        validator.validator(sysDict);
    }

    @Test(expected = SystemException.class)
    public void testSaveDictCodeNull() {
        final SysDict sysDict = new SysDict();
        validator.validator(sysDict);
    }

}
