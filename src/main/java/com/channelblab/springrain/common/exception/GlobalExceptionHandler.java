package com.channelblab.springrain.common.exception;


import com.channelblab.springrain.common.response.Response;
import com.channelblab.springrain.common.utils.MultilingualUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author dengy
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public Response unHandleException(Exception e) {
        log.error("系统异常，信息为:", e);
        return new Response() {
            @Override
            public Boolean getStatus() {
                return false;
            }

            @Override
            public Integer getCode() {
                return Response.COMMON_ERROR_CODE;
            }

            @Override
            public String getMessage() {
                return MultilingualUtil.get("common_error_code_msg");
            }

            @Override
            public Object getData() {
                return null;
            }
        };
    }

    /**
     * 业务异常处理类
     *
     * @param be 业务异常类
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Response businessException(BusinessException be) {
        log.error("业务异常，异常信息为：{}", be.getMessage());
        return new Response() {
            @Override
            public Boolean getStatus() {
                return false;
            }

            @Override
            public Integer getCode() {
                return be.getCode();
            }

            @Override
            public String getMessage() {
                return MultilingualUtil.get(be.getMessage());
            }

            @Override
            public Object getData() {
                return null;
            }
        };
    }

    /**
     * 参数异常处理类
     *
     * @param me 参数异常类
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, IllegalArgumentException.class, ConstraintViolationException.class})
    public Response parametersException(Exception me) {
        log.error("请求参数异常，信息为:{}", me.getMessage());
        Map<String, String> errors = new HashMap<>();
        if (me instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) me;
            Map<String, String> finalErrors = errors;
            ex.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                finalErrors.put(fieldName, errorMessage);
            });
        }

        if (me instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) me;
            errors = ex.getBindingResult().getAllErrors().stream().filter(error -> error instanceof FieldError).map(error -> (FieldError) error)
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        }


        Map<String, String> finalErrors1 = errors;
        return new Response() {
            @Override
            public Boolean getStatus() {
                return false;
            }

            @Override
            public Integer getCode() {
                return COMMON_ERROR_CODE;
            }

            @Override
            public String getMessage() {
                return MultilingualUtil.get("common_error_code_msg");
            }

            @Override
            public Object getData() {
                return finalErrors1.toString();
            }
        };
    }

    /**
     * 请求方式异常
     *
     * @param me 参数异常类
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Response methodException(Exception me) {
        return new Response() {
            @Override
            public Boolean getStatus() {
                return false;
            }

            @Override
            public Integer getCode() {
                return COMMON_ERROR_CODE;
            }

            @Override
            public String getMessage() {
                return MultilingualUtil.get("common_error_code_msg");
            }

            @Override
            public Object getData() {
                return me.getMessage();
            }
        };
    }


}