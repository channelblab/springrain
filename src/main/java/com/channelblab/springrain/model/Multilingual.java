package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:16
 * @description：
 * @modified By：
 */
@Data
@ApiModel
@TableName("system_multilingual")
public class Multilingual {
    @TableId(type = IdType.AUTO)
    private String id;
    private String lang;
    private String symbol;
    private String value;

    //关于当前字段的描述
    private String describe;
}
