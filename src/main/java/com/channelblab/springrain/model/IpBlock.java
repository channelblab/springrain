package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-19 14:55
 * @description：
 * @modified By：
 */
@Data
@TableName("system_ip_block")
public class IpBlock {
    private String id;
    private String ip;

}
