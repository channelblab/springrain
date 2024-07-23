package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:16
 * @description：
 * @modified By：
 */
@Data
@TableName("perm_role")
public class Role {
    private String id;
    @NotBlank
    private String name;
    private String describe;
    private LocalDate createTime;

    @NotEmpty
    @TableField(exist = false)
    private List<Permission> permissions;


    //    @TableField(exist = false)
    //    private List<user> users;

}
