package com.channelblab.springrain.common.aop;

import com.channelblab.springrain.common.utils.IpUtil;
import com.channelblab.springrain.model.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2024-05-22 14:45
 * @description：
 * @modified By：
 */
@Order(4)
@Aspect
@Component
public class LogAspect {
    private static final Integer EXPIRE_MILLION_SECONDS = 1000;
    private static Cache<Object, Object> cache;

    static {
        cache = Caffeine.newBuilder().build();
    }

    @Around("execution(* *..controller.*..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {


        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        ObjectMapper om = new ObjectMapper();
        Object res;

        Map<String, String> parameters = getParameters(request);

        System.err.println(parameters);
        long startTimeMillis = System.currentTimeMillis();
        long endTimeMillis;
        try {
            res = joinPoint.proceed();
            endTimeMillis = System.currentTimeMillis();
        } catch (Throwable throwable) {
            endTimeMillis = System.currentTimeMillis();
            throw throwable;
        }
        long costTime = endTimeMillis - startTimeMillis;
        Log newLog = Log.builder().createTime(LocalDateTime.now())
                .apiProcessingMillis(costTime)
                .response(om.writeValueAsString(res)).request("")
                .sourceIp(IpUtil.remoteIP(request)).userId("0000").build();

        System.err.println(newLog);

        String requestBody = getRequestBody(joinPoint);
        System.err.println(requestBody);


        return res;
    }

    /**
     * 获取请求参数
     * @param request
     * @return
     */
    private Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            parameters.put(paramName, request.getParameter(paramName));
        }
        return parameters;
    }

    private String getRequestBody(ProceedingJoinPoint proceedingJoinPoint) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = proceedingJoinPoint.getArgs();
        Parameter[] parametersArr = method.getParameters();
        for (Object arg : args) {
            sb.append(objectMapper.writeValueAsString(arg));
        }
        return sb.toString();
    }

}
