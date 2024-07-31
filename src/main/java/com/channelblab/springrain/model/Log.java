package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.channelblab.springrain.common.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:16
 * @description：
 * @modified By：
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
@TableName("system_log")
public class Log {
    private String id;
    private String userId;
    private String sourceIp;
    private String requestUri;
    private String name;
    private RequestStatus status;
    private String request;
    private String response;
    private Long costTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private User user;
}
