package com.xy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author XY
 * @since 2022-02-26
 */
@Data
@EqualsAndHashCode(callSuper = false)      //callSuper = false 意思是不调用父类里面的属性，也就是说，父类中有a,b,两个属性，子类中有a,b,c三个属性，那么之后使用该类的equals和hashcode只看c属性
@Accessors(chain = true)           //clain=true  set方法返回的都是User类型
@TableName("m_user")              //关联数据库中的表
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "昵称不能为空")
    private String username;

    private String avatar;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    private String password;

    private int status;

    private LocalDateTime created;

    private LocalDateTime lastLogin;


}
