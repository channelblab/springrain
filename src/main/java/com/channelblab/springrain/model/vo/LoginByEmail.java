package com.channelblab.springrain.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2025-03-26 13:59
 * @description：
 * @modified By：
 */
@Data
public class LoginByEmail {
    @NotBlank(message = "email can not be blank")
    @Email(message = "email format not correct")
    @Schema(defaultValue = "abc@abc.com")
    private String email;

    @NotBlank(message = "password can not be blank")
    @Schema(defaultValue = "12345678")
    private String password;
}