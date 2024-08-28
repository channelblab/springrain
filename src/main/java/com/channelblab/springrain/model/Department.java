package com.channelblab.springrain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-19 14:55
 * @description：
 * @modified By：
 */
@Data
@TableName("system_department")
public class Department {
    private String id;
    private String parentId;
    private String managerId;
    @NotBlank
    private String name;
    private Boolean enable;

    @TableField(exist = false)
    private List<Department> children;
}
