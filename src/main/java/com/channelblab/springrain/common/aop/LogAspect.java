package com.channelblab.springrain.common.aop;

import com.channelblab.springrain.common.anotations.NoLog;
import com.channelblab.springrain.common.anotations.NoLogFields;
import com.channelblab.springrain.common.disruptor.MessageEvent;
import com.channelblab.springrain.common.disruptor.MessageEventType;
import com.channelblab.springrain.common.enums.RequestStatus;
import com.channelblab.springrain.common.holder.UserHolder;
import com.channelblab.springrain.common.utils.AnnotationUtil;
import com.channelblab.springrain.common.utils.IpUtil;
import com.channelblab.springrain.common.utils.MessageEventProducer;
import com.channelblab.springrain.model.Log;
import com.channelblab.springrain.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
@ConditionalOnProperty(name = "aop.log", matchIfMissing = true)
public class LogAspect {
    @Autowired
    private ObjectMapper om;
    @Autowired
    private MessageEventProducer messageEventProducer;
    private static final int MAX_LENGTH_THRESHOLD = 1000;

    @Around("execution(* *..controller.*..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        List<String> skipFields = new ArrayList<>();
        if (AnnotationUtil.containAnnotation(joinPoint, NoLog.class)) {
            return joinPoint.proceed();
        }
        if (AnnotationUtil.containAnnotation(joinPoint, NoLogFields.class)) {
            NoLogFields noLogFields = (NoLogFields) AnnotationUtil.getAnnotation(joinPoint, NoLogFields.class);
            if (noLogFields.fields().length != 0) {
                skipFields = Arrays.stream(noLogFields.fields()).collect(Collectors.toList());
            }
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Object res = null;
        long startTimeMillis = System.currentTimeMillis();
        long endTimeMillis;

        String apiName = null;
        Operation annotation = (Operation) AnnotationUtil.getAnnotation(joinPoint, Operation.class);
        if (annotation != null) {
            apiName = annotation.summary();
        }
        User user = UserHolder.getUser();
        try {
            res = joinPoint.proceed();
            endTimeMillis = System.currentTimeMillis();
            String method = request.getMethod();
            String requestDataString = null;
            if (method.equals("GET") || method.equals("DELETE")) {
                Map<String, String> parameters = getParameters(request, skipFields);
                if (parameters.keySet().size() != 0) {
                    //todo 不能序列化的东西不处理
                    requestDataString = om.writeValueAsString(parameters);
                }
            } else if (method.equals("POST") || method.equals("PUT")) {
                requestDataString = getRequestBody(joinPoint, skipFields);
            }

            long costTime = endTimeMillis - startTimeMillis;
            Log newLog = Log.builder().createTime(LocalDateTime.now()).costTime(costTime).requestUri(request.getRequestURI()).status(RequestStatus.SUCCESS)
                    .response(res != null ? om.writeValueAsString(res) : null).request(requestDataString).name(apiName).sourceIp(IpUtil.remoteIP(request)).userId(user == null ? null : user.getId())
                    .build();
            MessageEvent event = new MessageEvent();
            event.setMessageEventType(MessageEventType.LOG);
            event.setData(newLog);
            messageEventProducer.produce(event);

        } catch (Throwable throwable) {
            endTimeMillis = System.currentTimeMillis();
            String method = request.getMethod();
            String requestDataString = null;
            if (method.equals("GET") || method.equals("DELETE")) {
                Map<String, String> parameters = getParameters(request, skipFields);
                if (parameters.keySet().size() != 0) {
                    //todo 不能序列化的东西不处理
                    requestDataString = om.writeValueAsString(parameters);
                }
            } else if (method.equals("POST") || method.equals("PUT")) {
                requestDataString = getRequestBody(joinPoint, skipFields);
            }
            long costTime = endTimeMillis - startTimeMillis;
            Log newLog = Log.builder().createTime(LocalDateTime.now()).costTime(costTime).requestUri(request.getRequestURI()).status(RequestStatus.FAIL)
                    .response(res != null ? om.writeValueAsString(throwable.getMessage()) : null).request(requestDataString).name(apiName).sourceIp(IpUtil.remoteIP(request))
                    .userId(user == null ? null : user.getId()).build();

            MessageEvent event = new MessageEvent();
            event.setMessageEventType(MessageEventType.LOG);
            event.setData(newLog);
            messageEventProducer.produce(event);
            throw throwable;
        }
        return res;
    }

    /**
     * 获取请求参数
     *
     * @param request
     * @param skipFields
     * @return
     */
    private Map<String, String> getParameters(HttpServletRequest request, List<String> skipFields) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (!skipFields.contains(paramName)) {
                parameters.put(paramName, request.getParameter(paramName));
            }
        }

        if ("DELETE".equalsIgnoreCase(request.getMethod())) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> bodyParams = objectMapper.readValue(request.getInputStream(), Map.class);

            for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
                String paramName = entry.getKey();
                if (!skipFields.contains(paramName)) {
                    parameters.put(paramName, entry.getValue());
                }
            }
        }

        return parameters;
    }

    private String getRequestBody(ProceedingJoinPoint joinPoint, List<String> skipFields) throws IOException {

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        //do not serial null properties
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        StringBuilder sb = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            //todo  排除非JSON可序列化的参数，例如 HttpServletRequest 或 HttpServletResponse
            if (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)) {
                skipFields.forEach(item -> {
                    try {
                        Field field = arg.getClass().getDeclaredField(item);
                        field.setAccessible(true);
                        field.set(arg, null);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                });

                sb.append(om.writeValueAsString(arg));
            }
        }
        return sb.toString();
    }

}
