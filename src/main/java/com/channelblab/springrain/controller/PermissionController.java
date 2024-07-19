package com.channelblab.springrain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.channelblab.springrain.common.anotations.NoAuth;
import com.channelblab.springrain.common.anotations.NoLog;
import com.channelblab.springrain.common.anotations.NoLogin;
import com.channelblab.springrain.model.Permission;
import com.channelblab.springrain.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限点
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:12
 * @description：
 * @modified By：
 */
@Api(value = "dd", tags = "dd1")
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    @NoLog
    @GetMapping("/page")
    public IPage<Permission> page(Integer page, Integer size) {
        return permissionService.page(page, size);
    }



    @ApiOperation(value = "权限树", notes = "dfddddddfffffffffff")
    @NoLogin
    @NoAuth
    @GetMapping("/tree")
    public List<Permission> tree() {
        return permissionService.tree();
    }

    public static void main(String[] args) {
        AntPathMatcher matcher = new AntPathMatcher();
        boolean match = matcher.match("/api/**", "/api/v1/resource");
        System.out.println(match);
    }

}
