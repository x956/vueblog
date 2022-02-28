package com.xy.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: vueblog
 * *
 * @author: XY
 * @create: 2022-02-27 21:52
 **/

public class JwtToken implements AuthenticationToken {

    private String token;
    public JwtToken(String jwt){
        this.token=jwt;
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
