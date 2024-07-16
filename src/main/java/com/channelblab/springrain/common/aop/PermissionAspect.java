package com.channelblab.springrain.common.aop;

import com.channelblab.springrain.common.anotations.NoAuth;
import com.channelblab.springrain.common.utils.AnnotationUtil;
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
@Order(3)
@Aspect
@Component
public class PermissionAspect {
    private static final Integer EXPIRE_MILLION_SECONDS = 1000;
    private static Cache<Object, Object> cache;

    static {
        cache = Caffeine.newBuilder().build();
    }

    @Before("execution(* *..controller.*..*(..))")
    public void doLimit(JoinPoint joinPoint) throws Throwable {
        if (AnnotationUtil.containAnnotation(joinPoint, NoAuth.class)) {
            return;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();


    }

}
