package com.xy.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.common.lang.Result;
import com.xy.entity.Blog;
import com.xy.service.BlogService;
import com.xy.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.TabExpander;
import java.time.LocalDateTime;

/**
 * <p>
 *  blog控制器
 * </p>
 *
 * @author XY
 * @since 2022-02-26
 */
@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){

        Page page = new Page(currentPage,5);
        IPage pageDate = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(pageDate);
    }

    @GetMapping("/blogs/{id}")
    public Result detial(@PathVariable(name = "id") Long id){

        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客已被删除");

        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blogs/edit")
    public Result edit(@Validated @RequestBody Blog blog){

        //blog中传来了用户编辑的信息

        Blog temp = null;
        //如果这个博客的id不为空，也就是说，当前博客是已经存在的需要修改编辑的
        if(blog.getId() !=null){
            temp = blogService.getById(blog.getId());
            //判断当前文章是否为当前登录用户的
            Assert.isTrue(temp.getUserId().equals(ShiroUtils.getProfile().getId()),"没有权限编辑");
        }else{         //否则，那就设定当前

            temp =new Blog();           //这些信息都是需要系统设定
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }


        //将 文章所属用户的ID 以及 文章创建的时间 信息存放到blog中，因为用户并没有设置
        BeanUtils.copyProperties(blog,temp,"id","userId","created","status");
        blogService.saveOrUpdate(temp);

        return Result.succ(null);
    }


}
