package com.edgar.core.auth;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-4
 * Time: 下午1:57
 * To change this template use File | Settings | File Templates.
 */
public class RefreshVo {
    private String accessToken;

    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
