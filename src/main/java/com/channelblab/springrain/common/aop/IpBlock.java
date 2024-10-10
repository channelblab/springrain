package com.channelblab.springrain.common.aop;

import com.channelblab.springrain.common.exception.BusinessException;
import com.channelblab.springrain.common.response.Response;
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
 *  block 10 times in 10 mines add to the black list
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2024-05-22 14:45
 * @description：
 * @modified By：
 */
@Order(1)
@Aspect
@Component
public class IpBlock {
    private static final Integer DEFAULT_EXPIRE_MILLION_SECONDS = 300;
    private static final Cache<Object, Object> cache;

    static {
        cache = Caffeine.newBuilder().build();
    }

    @Before("execution(* *..controller.*..*(..))")
    public void doLimit(JoinPoint joinPoint) {


        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        String remoteIP = IpUtil.remoteIP(request);
        Integer countTimes = (Integer) cache.getIfPresent(remoteIP);
        if (countTimes == null) {
            return;
        } else {

        }
        // black list

        Object ifPresent = cache.getIfPresent(remoteIP + "/" + requestURI);
        long currentMillis = System.currentTimeMillis();
        if (ifPresent != null) {
            long lastMillions = (long) ifPresent;
            cache.put(remoteIP + "/" + requestURI, currentMillis);
            if (currentMillis - lastMillions < DEFAULT_EXPIRE_MILLION_SECONDS) {
                // add to the black list for safety
                throw new BusinessException(Response.RATE_LIMIT_CODE, "rate_limit_code_msg");
            }
        } else {
            cache.put(remoteIP + "/" + requestURI, currentMillis);
        }
    }


    public static void addBlock(String ip) {
        IpBlock.cache.getIfPresent(ip);

    }

}
