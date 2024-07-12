package com.channelblab.springrain.common.exception;

import com.channelblab.springrain.common.response.Response;

/**
 * 业务异常类
 *
 * @author dengy
 */
public class BusinessException extends RuntimeException {

    private Integer code;

    private String message;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        super(message);
        this.code = Response.ERROR_CODE;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
