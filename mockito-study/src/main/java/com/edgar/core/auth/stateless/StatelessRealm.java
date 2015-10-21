package com.edgar.core.auth.stateless;

import com.edgar.core.auth.AuthService;
import com.edgar.module.sys.repository.domain.SysRole;
import org.apache.commons.codec.binary.Hex;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 无状态的身份认证
 * User: Administrator
 * Date: 14-8-12
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
public class StatelessRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(StatelessRealm.class);
    @Autowired
    private AuthService authService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        LOGGER.debug("StatelessRealm doGetAuthenticationInfo");
        String accessToken = (String) getAvailablePrincipal(principalCollection);
        StatelessUser user = authService.getUser(accessToken);
        Set<String> roleNames = new LinkedHashSet<String>();
        for (SysRole sysRole : user.getRoles()) {
            roleNames.add(sysRole.getRoleName());
        }

        Set<String> permissions = authService.getPermissions(user.getUserId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOGGER.debug("StatelessRealm doGetAuthenticationInfo");

        StatelessToken statelessToken = (StatelessToken) authenticationToken;
        String accessToken = statelessToken.getAccessToken();
        String baseString = statelessToken.getBaseString();

        String key = authService.getSecretKey(accessToken);
        String serverDigest = digest(key,
                baseString);
        return new SimpleAuthenticationInfo(accessToken, serverDigest, getName());
    }

    private String digest(String key, String content) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = key.getBytes("utf-8");
            byte[] dataBytes = content.getBytes("utf-8");

            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);

            byte[] doFinal = mac.doFinal(dataBytes);
            byte[] hexB = new Hex().encode(doFinal);
            return new String(hexB, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
