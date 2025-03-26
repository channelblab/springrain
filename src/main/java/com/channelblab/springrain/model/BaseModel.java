package com.channelblab.springrain.model;

import com.channelblab.springrain.common.holder.UserHolder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2025-03-26 8:09
 * @description：
 * @modified By：
 */
@Data
public class BaseModel {
    //id
    private String id;

    //创建时间
    private LocalDateTime createDateTime = LocalDateTime.now();
    //更新时间
    private LocalDateTime updateDateTime = LocalDateTime.now();
    //创建人ID
    private String createUserId = UserHolder.getUser() != null ? UserHolder.getUser().getId() : null;
    //更新用户ID
    private String updateUserId = UserHolder.getUser() != null ? UserHolder.getUser().getId() : null;
    //删除状态
    private Boolean deleted = false;
}