package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-19 14:55
 * @description：
 * @modified By：
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("system_ip_block")
public class IpBlock extends BaseModel {
    private String ip;
    private LocalDateTime blockDateTime;

}