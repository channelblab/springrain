package com.channelblab.springrain.common.aop;

import com.channelblab.springrain.common.anotations.NoLogin;
import com.channelblab.springrain.common.exception.BusinessException;
import com.channelblab.springrain.common.response.Response;
import com.channelblab.springrain.common.utils.AnnotationUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2024-05-22 14:45
 * @description：
 * @modified By：
 */
@Order(2)
@Aspect
@Component
public class LoginAspect {
    private static final Integer EXPIRE_MILLION_SECONDS = 1000;
    private static Cache<Object, Object> cache;

    static {
        cache = Caffeine.newBuilder().build();
    }

    @Before("execution(* *..controller.*..*(..))")
    public void doLimit(JoinPoint joinPoint) throws NoSuchMethodException {
        if (AnnotationUtil.containAnnotation(joinPoint, NoLogin.class)) {
            return;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("token");
        if (ObjectUtils.isEmpty(token)) {
            throw new BusinessException(Response.LOGIN_EXPIRE_CODE,
                    "login_expire");
        }
        //todo
    }

}
