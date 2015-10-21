package com.edgar.core.auth;

import com.edgar.core.auth.stateless.StatelessUser;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-5
 * Time: 下午12:24
 * To change this template use File | Settings | File Templates.
 */
public interface AuthService {
    boolean isNewRequest(String nonce, long timestamp);

    AccessToken login(LoginCommand command);

    void logout(String accessToken);

    Set<String> getPermissions(int userId);

    String getSecretKey(String accessToken);

    String getUsername(String accessToken);

    StatelessUser getUser(String accessToken);

    AccessToken refresh(RefreshVo command);
}
