package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.channelblab.springrain.common.enums.PermissionType;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:16
 * @description：
 * @modified By：
 */
@TableName("perm_permission")
public class PermissionPoint {
    private String id;
    private PermissionType type;

    private String uri;
}
