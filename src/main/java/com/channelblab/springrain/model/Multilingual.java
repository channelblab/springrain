package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class Multilingual {
    @TableId(type = IdType.AUTO)
    private String id;
    private String langSymbol;
    private String langDescribe;
    private String symbol;
    private String symbolValue;
    //关于当前字段的描述
    private String symbolDescribe;


    //system custom
    private String type;
}
