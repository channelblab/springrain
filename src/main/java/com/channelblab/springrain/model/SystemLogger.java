package com.channelblab.springrain.model;

import com.channelblab.springrain.common.enums.LogLevel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-23 11:42
 * @description：
 * @modified By：
 */
@Data
public class SystemLogger {

    @NotNull
    private LogLevel level;

}
