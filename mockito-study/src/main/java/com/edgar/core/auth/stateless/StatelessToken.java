package com.edgar.core.auth.stateless;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 无状态的TOKEN，用来校验rest API
 */
public class StatelessToken implements AuthenticationToken {
    private String accessToken;
    private String baseString;
    private String digest;

    public StatelessToken(String accessToken, String baseString, String digest) {
        this.accessToken = accessToken;
        this.baseString = baseString;
        this.digest = digest;
    }

    public String getBaseString() {
        return baseString;
    }

    public void setBaseString(String baseString) {
        this.baseString = baseString;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    @Override
    public Object getPrincipal() {
        return accessToken;
    }

    @Override
    public Object getCredentials() {
        return digest;
    }
}
