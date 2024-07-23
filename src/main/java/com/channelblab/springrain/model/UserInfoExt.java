package com.channelblab.springrain.model;

import lombok.Data;

import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-23 14:21
 * @description：
 * @modified By：
 */
@Data
public class UserInfoExt {
    private User user;
    private List<String> buttonPermissions;
    private List<Permission> menuPermissions;
}
