package com.channelblab.springrain.common.aop;

import com.channelblab.springrain.common.anotations.NoLog;
import com.channelblab.springrain.common.enums.RequestStatus;
import com.channelblab.springrain.common.holder.UserHolder;
import com.channelblab.springrain.common.utils.AnnotationUtil;
import com.channelblab.springrain.common.utils.IpUtil;
import com.channelblab.springrain.dao.LogDao;
import com.channelblab.springrain.model.Log;
import com.channelblab.springrain.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
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
 * 从设计原理上来说，日志只应该记录一些危险操作。
 * 由于分页等查询操作数据量大，也不适合做日志记录，因为数据会累积，累积几次以后就会触及数据库等的一次性数据传输限制
 * 因此建议将分页查询等大数据量接口进行非日志记录过滤
 *
 *
 *
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2024-05-22 14:45
 * @description：
 * @modified By：
 */
@Order(4)
@Aspect
@Component
public class LogAspect {
    @Autowired
    private LogDao logDao;
    @Autowired
    private ObjectMapper om;

    @Around("execution(* *..controller.*..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        if (AnnotationUtil.containAnnotation(joinPoint, NoLog.class)) {
            return joinPoint.proceed();
        }
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

        String apiName = null;
        Object annotation = AnnotationUtil.getAnnotation(joinPoint, ApiOperation.class);
        if (annotation != null) {
            ApiOperation apiOperation = (ApiOperation) annotation;
            apiName = apiOperation.value();
        }
        User user = UserHolder.getUser();
        try {
            res = joinPoint.proceed();
            endTimeMillis = System.currentTimeMillis();
            long costTime = endTimeMillis - startTimeMillis;
            Log newLog = Log.builder().createTime(LocalDateTime.now()).costTime(costTime).requestUri(request.getRequestURI()).status(RequestStatus.SUCCESS).response(om.writeValueAsString(res))
                    .request(requestDataString).name(apiName).sourceIp(IpUtil.remoteIP(request)).userId(user == null ? null : user.getId()).build();
            logDao.insert(newLog);

        } catch (Throwable throwable) {
            endTimeMillis = System.currentTimeMillis();
            long costTime = endTimeMillis - startTimeMillis;
            Log newLog = Log.builder().createTime(LocalDateTime.now()).costTime(costTime).requestUri(request.getRequestURI()).status(RequestStatus.FAIL)
                    .response(om.writeValueAsString(throwable.getMessage())).request(requestDataString).name(apiName).sourceIp(IpUtil.remoteIP(request)).userId(user == null ? null : user.getId())
                    .build();
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

    private String getRequestBody(ProceedingJoinPoint joinPoint) throws IOException {

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
