package com.edgar.module.sys.service;

import com.edgar.module.sys.repository.domain.SysUser;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-9
 * Time: 下午1:50
 * To change this template use File | Settings | File Templates.
 */
public interface PasswordService {
    public void encryptPassword(SysUser user);

    public String getEncryptPassword(String password, SysUser user);

    public String randomPassword();
}
