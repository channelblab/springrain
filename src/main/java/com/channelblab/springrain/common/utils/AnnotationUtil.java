package com.channelblab.springrain.common.utils;

import org.aspectj.lang.JoinPoint;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 13:07
 * @description：
 * @modified By：
 */
public class AnnotationUtil {

    public static boolean containAnnotation(JoinPoint joinPoint,
                                            Class clazz) throws NoSuchMethodException {

        Method method = getMethodFromJoinPoint(joinPoint);
        // 获取类上的注解
        Annotation[] classAnnotations = method.getDeclaringClass()
                .getAnnotations();
        if (Arrays.stream(classAnnotations).anyMatch(
                annotation -> annotation.annotationType().equals(clazz))) {
            return true;
        }
        // 获取方法上的注解
        Annotation[] methodAnnotations = method.getAnnotations();
        if (Arrays.stream(methodAnnotations).anyMatch(
                annotation -> annotation.annotationType().equals(clazz))) {
            return true;
        }


        return false;
    }

    public static Object getAnnotation(JoinPoint joinPoint,
                                       Class clazz) throws NoSuchMethodException {
        Method method = getMethodFromJoinPoint(joinPoint);
        return method.getAnnotation(clazz);
    }


    private static Method getMethodFromJoinPoint(
            JoinPoint joinPoint) throws NoSuchMethodException {
        String methodName = joinPoint.getSignature().getName();
        Class<?>[] parameterTypes = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getParameterTypes();
        return joinPoint.getTarget().getClass()
                .getMethod(methodName, parameterTypes);
    }


}
