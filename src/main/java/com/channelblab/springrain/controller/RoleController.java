package com.channelblab.springrain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.channelblab.springrain.model.Role;
import com.channelblab.springrain.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * controller for role
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:12
 * @description：
 * @modified By：
 */
@Tag(name = "框架-角色接口")
@Validated
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @Operation(summary = "新增或修改角色")
    @PostMapping("/addOrUpdate")
    public void addOrUpdate(@RequestBody @Validated Role role) {
        roleService.addOrUpdate(role);
    }

    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public IPage<Role> page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(required = false) String name) {
        return roleService.page(page, size, name);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable @NotBlank String id) {
        roleService.delete(id);

    }

}
