package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-22 9:10
 * @description：
 * @modified By：
 */
@Data
@TableName("perm_user_role")
public class UserRole {
    private String id;
    private String userId;
    private String roleId;
}
