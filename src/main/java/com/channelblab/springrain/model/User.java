package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-19 14:55
 * @description：
 * @modified By：
 */
@Data
@TableName("system_user")
public class User {
    private String id;
    private String name;
    @Email
    private String email;
    private String pass;
    @TableField(exist = false)
    private Boolean online;

    private Boolean enable;
}
