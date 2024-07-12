package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;

import java.time.LocalDate;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:16
 * @description：
 * @modified By：
 */
@ApiModel
@TableName("system_log")
public class Log {
    private String id;
    private String userId;
    private String sourceIp;
    private String request;
    private String response;
    private Integer apiProcessingMillis;
    private LocalDate createTime;
}
