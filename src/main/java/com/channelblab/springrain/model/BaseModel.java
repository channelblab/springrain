package com.channelblab.springrain.model;

import com.channelblab.springrain.common.holder.UserHolder;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "主键ID")
    private String id;
    @Schema(description = "创建时间（服务器时区）")
    private LocalDateTime createDateTime = LocalDateTime.now();
    @Schema(description = "更新时间（服务器时区）")
    private LocalDateTime updateDateTime = LocalDateTime.now();
    @Schema(description = "创建时间（0时区）")
    private Long globalCreateDateTime = System.currentTimeMillis() / 1000;
    @Schema(description = "更新时间（0时区）")
    private Long globalUpdateDateTime = System.currentTimeMillis() / 1000;
    @Schema(description = "创建人ID")
    private String createUserId = UserHolder.getUser() != null ? UserHolder.getUser().getId() : null;
    @Schema(description = "更新人ID")
    private String updateUserId = UserHolder.getUser() != null ? UserHolder.getUser().getId() : null;
    @Schema(description = "删除状态")
    private Boolean deleted = false;
}