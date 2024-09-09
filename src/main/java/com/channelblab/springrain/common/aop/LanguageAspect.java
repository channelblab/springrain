package com.channelblab.springrain.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 统一返回处理以后，清空语言不能放在@After及@AfterReturning中执行，因为虽然方法执行完，但是并没有切实执行完
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2024-05-22 14:45
 * @description：
 * @modified By：
 */
@Order(1)
@Aspect
@Component
public class LanguageAspect {

    @Before("execution(* *..controller.*..*(..))")
    public void doInit() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //初始化语言
        String lang = request.getHeader("Lang");
        if (ObjectUtils.isEmpty(lang)) {
            LocaleContextHolder.setLocale(Locale.SIMPLIFIED_CHINESE);
        } else {
            LocaleContextHolder.setLocale(new Locale(lang.toLowerCase()));
        }
    }

    //    @AfterReturning("execution(* *..controller.*..*(..))")
    //    public void deInit() {
    //        //移除语言设置
    //        LocaleContextHolder.resetLocaleContext();
    //    }


}
