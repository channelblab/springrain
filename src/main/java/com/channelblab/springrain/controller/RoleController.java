package com.channelblab.springrain.controller;

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
@Validated
@RestController
@RequestMapping("/role")
public class RoleController {


    @PostMapping("add")
    public void add() {

    }

    @GetMapping("/page")
    public void page(Integer page, Integer size) {

    }

    @PutMapping("update")
    public void update() {

    }

    @DeleteMapping("/delete/{id}")
    public void add(@PathVariable @NotBlank String id) {

    }

}
