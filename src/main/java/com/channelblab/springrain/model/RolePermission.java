package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-22 9:32
 * @description：
 * @modified By：
 */
@Data
@TableName("perm_role_permission")
public class RolePermission {
    private String id;
    private String roleId;
    private String permissionId;
}
