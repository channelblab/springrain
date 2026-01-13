package com.channelblab.springrain.common.aop;

import com.channelblab.springrain.common.anotations.NoLogin;
import com.channelblab.springrain.common.enums.HeaderName;
import com.channelblab.springrain.common.exception.BusinessException;
import com.channelblab.springrain.common.holder.UserHolder;
import com.channelblab.springrain.common.response.Response;
import com.channelblab.springrain.common.utils.AnnotationUtil;
import com.channelblab.springrain.common.utils.UserUtil;
import com.channelblab.springrain.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
@Order(1)
@Aspect
@Component
public class LoginAspect {

    @Around("execution(* *..controller.*..*(..))")
    public Object doValidate(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!AnnotationUtil.containAnnotation(joinPoint, NoLogin.class)) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            String token = request.getHeader(HeaderName.HEADER_TOKEN.getValue());
            if (ObjectUtils.isEmpty(token)) {
                throw new BusinessException(Response.LOGIN_EXPIRE_CODE, "login_expire");
            }
            User user = UserUtil.decToken(token);
            UserHolder.setUser(user);
            Object proceed = joinPoint.proceed();
            UserHolder.remove();
            return proceed;
        }
        return joinPoint.proceed();
    }
}