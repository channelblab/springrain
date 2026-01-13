package com.channelblab.springrain.common.aop;

import com.channelblab.springrain.common.anotations.NoAuth;
import com.channelblab.springrain.common.anotations.NoLogin;
import com.channelblab.springrain.common.exception.BusinessException;
import com.channelblab.springrain.common.holder.UserHolder;
import com.channelblab.springrain.common.response.Response;
import com.channelblab.springrain.common.utils.AnnotationUtil;
import com.channelblab.springrain.model.Permission;
import com.channelblab.springrain.service.PermissionService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2024-05-22 14:45
 * @description：
 * @modified By：
 */
@Order(4)
@Aspect
@Component
@ConditionalOnProperty(name = "aop.apipermission", matchIfMissing = true)
public class PermissionAspect {
    @Autowired
    private PermissionService permissionService;
    //    private List<String> excludePaths;

    @Before("execution(* *..controller.*..*(..))")
    public void doPermission(JoinPoint joinPoint) throws Throwable {
        //只使用无需登录，也需要排除掉
        if (AnnotationUtil.containAnnotation(joinPoint, NoAuth.class) || AnnotationUtil.containAnnotation(joinPoint, NoLogin.class)) {
            return;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        // exclude request uri
        //        if (excludePaths.contains(requestURI)) {
        //            return;
        //        }
        //current login user validate
        if (UserHolder.getUser() == null) {
            throw new BusinessException(Response.LOGIN_EXPIRE_CODE, "login_expire");
        }
        //administrator special logic, maybe we can do another logic for special user id
        if (UserHolder.getUser().getId().equals("1")) {
            return;
        }
        List<Permission> permissions = permissionService.selectAllPermission(UserHolder.getUser().getId());
        if (!passValidation(requestURI, permissions)) {
            throw new BusinessException(Response.NO_PERMISSION, "no_permission_msg");
        }
    }

    //todo if fully user restful ,some uri can not pass validation
    private boolean passValidation(String requestURI, List<Permission> permissions) {
        // 使用stream处理并收集所有的uri到Set中，直接避免重复
        Set<String> uriSet = permissions.stream().flatMap(permission -> Arrays.stream(permission.getUris().split(","))).collect(Collectors.toSet());
        // 检查requestURI是否存在于uriSet中
        //todo ---------2024-10-14

        //        for (String uri : uriSet) {
        //            if (requestURI.contains(uri)) {
        //                return true;
        //            }
        //        }
        return uriSet.contains(requestURI);
    }

}