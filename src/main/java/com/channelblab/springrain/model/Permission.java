package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.channelblab.springrain.common.enums.PermissionType;
import lombok.Data;

import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:16
 * @description：
 * @modified By：
 */
@Data
@TableName("perm_permission")
public class Permission {
    @TableField(exist = false)
    List<Permission> children;

    private String id;
    private PermissionType type;
    private String name;
    private String uris;
    private String parentId;

    //if button
    private String symbol;
}
