package com.edgar.module.sys.service.impl;

import com.edgar.module.sys.repository.domain.SysUser;
import com.edgar.module.sys.service.PasswordService;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-9
 * Time: 下午1:50
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MD51024ServiceImpl implements PasswordService {


    /**
     * 加密算法
     */
    private static final String algorithmName = "MD5";

    /**
     * 迭代次数
     */
    private static final int hashIterations = 1024;

    @Override
    public void encryptPassword(SysUser user) {
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String password = new SimpleHash(algorithmName, user.getPassword(),
                ByteSource.Util.bytes(user.getUsername() + user.getSalt()),
                hashIterations).toHex();
        user.setPassword(password);
    }

    @Override
    public String getEncryptPassword(String password, SysUser user) {
        return new SimpleHash(algorithmName, password, ByteSource.Util.bytes(user
                .getUsername() + user.getSalt()), hashIterations).toHex();
    }

    @Override
    public String randomPassword() {
        char[] r = getChar();
        Random rr = new Random();
        char[] pw = new char[8];
        for (int i = 0; i < pw.length; i++) {
            int num = rr.nextInt(62);
            pw[i] = r[num];
        }
        return new String(pw);
    }

    public char[] getChar() {
        char[] passwordLit = new char[62];
        char fword = 'A';
        char mword = 'a';
        char bword = '0';
        for (int i = 0; i < 62; i++) {
            if (i < 26) {
                passwordLit[i] = fword;
                fword++;
            } else if (i < 52) {
                passwordLit[i] = mword;
                mword++;
            } else {
                passwordLit[i] = bword;
                bword++;
            }
        }
        return passwordLit;
    }

}
