package com.xy.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.xy.entity.User;
import com.xy.service.UserService;
import com.xy.utils.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: vueblog
 * *
 * @author: XY
 * @create: 2022-02-27 21:25
 **/
@Component
public class AccountRealm extends AuthorizingRealm {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    private final static Logger logger = LoggerFactory.getLogger(AccountRealm.class);

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("正在进行授权");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("正在进行认证");
        JwtToken jwtToken = (JwtToken) token;

        String userId=jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        User user = userService.getById(userId);

        if(user==null){
            throw new UnknownAccountException("账户不存在");
        }

        logger.info("用户状态：{}",user.getStatus());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if(user.getStatus()==-1){
            throw new LockedAccountException("账户已经被锁定");
        }

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);

        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());

    }
}
