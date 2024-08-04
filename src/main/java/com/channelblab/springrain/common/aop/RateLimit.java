package com.channelblab.springrain.common.aop;

import com.channelblab.springrain.common.anotations.NoRateLimit;
import com.channelblab.springrain.common.exception.BusinessException;
import com.channelblab.springrain.common.response.Response;
import com.channelblab.springrain.common.utils.AnnotationUtil;
import com.channelblab.springrain.common.utils.IpUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2024-05-22 14:45
 * @description：
 * @modified By：
 */
@Order(1)
@Aspect
@Component
public class RateLimit {
    private static final Integer EXPIRE_MILLION_SECONDS = 300;
    private static final Cache<Object, Object> cache;

    static {
        cache = Caffeine.newBuilder().build();
    }

    @Before("execution(* *..controller.*..*(..))")
    public void doLimit(JoinPoint joinPoint) throws NoSuchMethodException {
        if (AnnotationUtil.containAnnotation(joinPoint, NoRateLimit.class)) {
            return;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        String remoteIP = IpUtil.remoteIP(request);
        Object ifPresent = cache.getIfPresent(remoteIP + "/" + requestURI);
        long currentMillis = System.currentTimeMillis();
        if (ifPresent != null) {
            long lastMillions = (long) ifPresent;
            cache.put(remoteIP + "/" + requestURI, currentMillis);
            if (currentMillis - lastMillions < EXPIRE_MILLION_SECONDS) {
                throw new BusinessException(Response.RATE_LIMIT_CODE, "request_rate_limit");
            }
        } else {
            cache.put(remoteIP + "/" + requestURI, currentMillis);
        }
    }

}
