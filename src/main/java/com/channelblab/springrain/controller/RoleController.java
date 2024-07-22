package com.channelblab.springrain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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


    @Operation(summary = "新增或修改角色")
    @PostMapping("add")
    public void add() {

    }

    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public void page(Integer page, Integer size) {

    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable @NotBlank String id) {

    }

}
