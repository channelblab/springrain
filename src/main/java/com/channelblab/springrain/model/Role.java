package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.channelblab.springrain.common.enums.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:16
 * @description：
 * @modified By：
 */
@Data
@TableName("perm_role")
public class Role extends BaseModel {

    @Schema(description = "name")
    @NotBlank
    private String name;

    @Schema(description = "角色描述")
    private String roleDescribe;


    //内置不能删除
    @Schema(description = "角色类型")
    private RoleType type;//自定义或者内置

    @NotEmpty
    @TableField(exist = false)
    private List<Permission> permissions;

    @TableField(exist = false)
    private List<User> users;

}