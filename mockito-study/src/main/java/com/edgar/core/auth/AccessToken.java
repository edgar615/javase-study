package com.edgar.core.auth;

import java.io.Serializable;

public class AccessToken implements Serializable {


    private String accessToken;

    /**
     * 用于刷新Access Token 的 Refresh Token
     */
    private String refreshToken;

    /**
     * 基于http调用API时计算参数签名用的签名密钥
     */
    private String secretKey;

    /**
     * Access Token的有效期，以秒为单位
     */
    private String expiresIn;

    /**
     * 用户名
     */
    private String username;

    /**
     * 创建时间
     */
    private String serverTime;

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
