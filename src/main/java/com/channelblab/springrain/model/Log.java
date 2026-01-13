package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.channelblab.springrain.common.enums.RequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:16
 * @description：
 * @modified By：
 */
@Data
@Schema
@TableName("system_log")
@NoArgsConstructor
public class Log extends BaseModel {
    private String sourceIp;
    private String requestUri;
    private String interfaceName;
    private RequestStatus executeStatus;
    private String request;
    private String response;
    private Long costTime;
    @TableField(exist = false)
    private User user;

    public Log(String sourceIp, String requestUri, String name, RequestStatus status, String request, String response, Long costTime) {
        this.sourceIp = sourceIp;
        this.requestUri = requestUri;
        this.interfaceName = name;
        this.executeStatus = status;
        this.request = request;
        this.response = response;
        this.costTime = costTime;
    }
}