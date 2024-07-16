package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:16
 * @description：
 * @modified By：
 */
@Data
@TableName("perm_role")
public class Role {
    private String id;
    private String name;
    private String describe;
    private LocalDate createTime;
}
