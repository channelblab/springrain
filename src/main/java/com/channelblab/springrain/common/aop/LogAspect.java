package com.channelblab.springrain.common.aop;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.channelblab.springrain.common.enums.RequestStatus;
import com.channelblab.springrain.common.utils.IpUtil;
import com.channelblab.springrain.dao.LogDao;
import com.channelblab.springrain.model.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Autowired
    private LogDao logDao;
    @Autowired
    private ObjectMapper om;

    @Around("execution(* *..controller.*..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String method = request.getMethod();
        String requestDataString = null;
        if (method.equals("GET") || method.equals("DELETE")) {
            Map<String, String> parameters = getParameters(request);
            requestDataString = om.writeValueAsString(parameters);
        } else if (method.equals("POST") || method.equals("PUT")) {
            requestDataString = getRequestBody(joinPoint);
        }
        Object res;
        long startTimeMillis = System.currentTimeMillis();
        long endTimeMillis;
        try {
            res = joinPoint.proceed();
            endTimeMillis = System.currentTimeMillis();
            long costTime = endTimeMillis - startTimeMillis;
            //todo 这里有bug数据太多时
           if( res instanceof IPage){

               Log newLog = Log.builder().createTime(LocalDateTime.now())
                       .costTime(costTime).requestUri(request.getRequestURI())
                       .status(RequestStatus.SUCCESS)
                       .response(om.writeValueAsString(((IPage<?>) res).getRecords()))
                       .request(requestDataString)
                       .sourceIp(IpUtil.remoteIP(request)).userId("0000").build();
               logDao.insert(newLog);

           }else{
               Log newLog = Log.builder().createTime(LocalDateTime.now())
                       .costTime(costTime).requestUri(request.getRequestURI())
                       .status(RequestStatus.SUCCESS)
                       .response(om.writeValueAsString(res))
                       .request(requestDataString)
                       .sourceIp(IpUtil.remoteIP(request)).userId("0000").build();
               logDao.insert(newLog);
           }




        } catch (Throwable throwable) {
            endTimeMillis = System.currentTimeMillis();
            long costTime = endTimeMillis - startTimeMillis;
            Log newLog = Log.builder().createTime(LocalDateTime.now())
                    .costTime(costTime).requestUri(request.getRequestURI())
                    .status(RequestStatus.FAIL)
                    .response(om.writeValueAsString(throwable.getMessage()))
                    .request(requestDataString)
                    .sourceIp(IpUtil.remoteIP(request)).userId("0000").build();
            logDao.insert(newLog);
            throw throwable;
        }
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

    private String getRequestBody(
            ProceedingJoinPoint joinPoint) throws IOException {

        ObjectMapper om = new ObjectMapper();

        StringBuilder sb = new StringBuilder();
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            // 排除非JSON可序列化的参数，例如 HttpServletRequest 或 HttpServletResponse
            if (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)) {
                sb.append(om.writeValueAsString(arg));
            }
        }

        return sb.toString();
    }

}
