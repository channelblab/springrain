package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.channelblab.springrain.common.enums.RequestStatus;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:16
 * @description：
 * @modified By：
 */
@Data
@Builder
@ApiModel
@TableName("system_log")
public class Log {
    private String id;
    private String userId;
    private String sourceIp;
    private String requestUri;
    private RequestStatus status;
    private String request;
    private String response;
    private Long costTime;
    private LocalDateTime createTime;
}
