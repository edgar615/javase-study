package com.edgar.core.auth.password;

import com.edgar.module.sys.facade.UserFacde;
import com.edgar.module.sys.repository.domain.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义的Shiro Realm
 *
 * @author Edgar Zhang
 * @version 1.0
 */
public class UsernamePasswordRealm extends JdbcRealm {

    private static final Logger logger = LoggerFactory
            .getLogger(UsernamePasswordRealm.class);

    @Autowired
    private UserFacde userFacde;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        logger.debug("UsernamePasswordRealm doGetAuthenticationInfo");
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        if (username == null) {
            throw new AccountException("username cannot be null");
        }

        AuthenticationInfo info;
        SysUser user = getUser(username);

        if (user == null || user.getPassword() == null) {
            throw new UnknownAccountException("username  [" + username
                    + "] not exists");
        }
        info = new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(username + user.getSalt()),
                getName());

        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        return null;

    }

    /**
     * 根据用户密码，获取用户
     *
     * @param username 用户名
     * @return 登录用户
     */
    protected SysUser getUser(String username) {
        SysUser sysUser = userFacde.queryByUsername(username);
        if (sysUser == null) {
            throw new UnknownAccountException("username cannot be null");
        }
        logger.debug("user login : {}", username);
        return sysUser;

    }

}