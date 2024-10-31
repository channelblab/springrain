package com.channelblab.springrain.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-10-31 13:03
 * @description：
 * @modified By：
 */
@Data
public class File {
    private String id;
    private String fileName;

    private LocalDateTime createDateTime;

}
