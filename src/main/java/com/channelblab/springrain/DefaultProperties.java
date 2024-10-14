package com.channelblab.springrain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-08-28 15:34
 * @description：
 * @modified By：
 */
@Data
@Component
public class DefaultProperties {
    private String newUserPass = "abc123";
}
