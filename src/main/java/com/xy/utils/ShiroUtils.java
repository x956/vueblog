package com.xy.utils;

import com.xy.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @program: vueblog
 * *
 * @author: XY
 * @create: 2022-02-28 19:55
 **/

public class ShiroUtils {

    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}
