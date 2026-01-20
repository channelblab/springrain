package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.channelblab.springrain.common.enums.MultilingualType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:16
 * @description：
 * @modified By：
 */
@Data
@Schema
@TableName("system_multilingual")
public class Multilingual extends BaseModel {
    // FRONT frontend, BACKEND backend
    private MultilingualType type;
    private String lang;
    private String langDescribe;
    private String symbol;
    private String symbolValue;
    private String symbolDescribe;
}