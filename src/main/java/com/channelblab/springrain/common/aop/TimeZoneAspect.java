package com.channelblab.springrain.common.aop;

import com.channelblab.springrain.common.holder.ZoneIdHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
public class TimeZoneAspect {

    @Around("execution(* *..controller.*..*(..))")
    public Object doLogic(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String timeZoneHeader = request.getHeader("Time-Zone");
        ZoneIdHolder.setZone(timeZoneHeader);
        try {
            //process real logic
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            ZoneIdHolder.clear();
        }
    }
}