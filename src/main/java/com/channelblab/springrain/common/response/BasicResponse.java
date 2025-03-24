package com.channelblab.springrain.common.response;

import lombok.Data;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2025-03-24 11:15
 * @description：
 * @modified By：
 */
@Data
public class BasicResponse implements Response{

    private Boolean status;
    private Integer code;
    private String message;
    private Object data;

    public BasicResponse(Boolean status, Integer code, String message, Object data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public Boolean getStatus() {
        return status;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Object getData() {
        return data;
    }
}
