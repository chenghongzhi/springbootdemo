package com.example.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author chenghongzhi
 * @type OAuth2Token
 * @desc
 * @date 2020/8/24 6:39 下午
 */

public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

