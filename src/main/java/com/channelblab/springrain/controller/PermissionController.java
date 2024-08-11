package com.channelblab.springrain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.channelblab.springrain.common.anotations.NoLog;
import com.channelblab.springrain.model.Permission;
import com.channelblab.springrain.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "框架-权限接口")
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @NoLog
    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public IPage<Permission> page(Integer page, Integer size, String name) {
        return permissionService.page(page, size, name);
    }


    @NoLog
    @Operation(summary = "权限树")
    @GetMapping("/tree")
    public List<Permission> tree() {
        return permissionService.tree();
    }

}
