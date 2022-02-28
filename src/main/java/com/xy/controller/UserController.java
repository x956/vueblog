package com.xy.controller;


import com.xy.common.lang.Result;
import com.xy.entity.User;
import com.xy.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XY
 * @since 2022-02-26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public Result index(){
        User user =userService.getById(1);
        return Result.succ(user);
    }

    @PostMapping ("/save")
    public Result save(@Validated @RequestBody User user){
        return Result.succ(user);
    }


}
