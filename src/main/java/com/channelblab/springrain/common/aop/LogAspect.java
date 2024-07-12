package com.channelblab.springrain.common.aop;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
    public void doLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTimeMillis = System.currentTimeMillis();
        long endTimeMillis;
        try {
            joinPoint.proceed();
            endTimeMillis = System.currentTimeMillis();
        } catch (Throwable throwable) {
            endTimeMillis = System.currentTimeMillis();
            throw throwable;
        }

        System.err.println(endTimeMillis - startTimeMillis);


    }

}
