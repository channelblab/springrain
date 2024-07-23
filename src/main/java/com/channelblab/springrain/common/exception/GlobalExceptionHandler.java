package com.channelblab.springrain.common.exception;


import com.channelblab.springrain.common.response.Response;
import com.channelblab.springrain.common.utils.MultilingualUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 *
 * @author dengy
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public Response unHandleException(Exception e) {
        LOGGER.error("系统异常，信息为:", e);
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
                return MultilingualUtil.getValue(e.getMessage());
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
        LOGGER.error("业务异常，异常信息为：{}", be.getMessage());
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
                return MultilingualUtil.getValue(be.getMessage());
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
        LOGGER.error("请求参数异常，信息为:{}", me.getMessage());
        Map<String, String> errors = new HashMap<>();
        if (me instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) me;

            ex.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        }


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
                return errors.toString();
            }

            @Override
            public Object getData() {
                return null;
            }
        };
    }

    /**
     * 请求方式异常
     *
     * @param me 参数异常类
     * @return
     */
    //    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    //    public Response methodException(Exception me) {
    //        return new Response() {
    //            @Override
    //            public Boolean getStatus() {
    //                return false;
    //            }
    //
    //            @Override
    //            public Integer getCode() {
    //                return ERROR_CODE;
    //            }
    //
    //            @Override
    //            public String getMessage() {
    //                return me.getMessage();
    //            }
    //
    //            @Override
    //            public Object getData() {
    //                return null;
    //            }
    //        };
    //    }


}
