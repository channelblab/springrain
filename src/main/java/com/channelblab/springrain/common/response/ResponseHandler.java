package com.channelblab.springrain.common.response;

import com.channelblab.springrain.common.anotations.NoResponseHandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2024-04-22 13:18
 * @description：标准返回处理类
 * @modified By：
 */
@Component
@RestControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {
    @Autowired
    private Cache multilingualCache;


    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.hasMethodAnnotation(NoResponseHandle.class);
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof Response) {
            return body;
        } else if (body instanceof String) {
            //特殊处理string返回类型
            Response responseString = new Response() {
                @Override
                public Boolean getStatus() {
                    return true;
                }

                @Override
                public Integer getCode() {
                    return SUCCESS_CODE;
                }

                @Override
                public String getMessage() {
                    //todo 是否需要多语言处理
//                    return I18nMessageUtil.get("system.operation.success");
                    return "操作成功";
                }

                @Override
                public Object getData() {
                    return body;
                }
            };
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(responseString);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new Response() {
                @Override
                public Boolean getStatus() {
                    return true;
                }

                @Override
                public Integer getCode() {
                    return SUCCESS_CODE;
                }

                @Override
                public String getMessage() {
                    //todo 是否需要多语言处理
//                    return I18nMessageUtil.get("system.operation.success");
                    return "操作成功";
                }

                @Override
                public Object getData() {
                    return body;
                }
            };
        }
    }
}
