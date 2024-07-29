package com.channelblab.springrain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.channelblab.springrain.common.anotations.NoAuth;
import com.channelblab.springrain.common.anotations.NoLog;
import com.channelblab.springrain.common.anotations.NoLogin;
import com.channelblab.springrain.model.User;
import com.channelblab.springrain.model.UserInfoExt;
import com.channelblab.springrain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:12
 * @description：log controller
 * @modified By：
 */
@Tag(name = "框架-用户接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @NoLog
    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public IPage<User> page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(required = false) String userId,
            @RequestParam(required = false) String name) {
        return userService.page(page, size, userId, name);
    }

    @NoAuth
    @Operation(summary = "查询个人信息")
    @GetMapping("/info")
    public UserInfoExt info() {
        return userService.info();
    }

    @NoLog
    @Operation(summary = "通过部门id查询所有员工")
    @GetMapping("/byDepartmentId")
    public IPage<User> byDepartmentId(@RequestParam() @NotBlank String departmentId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return userService.byDepartmentId(departmentId, page, size);
    }


    @NoLogin
    @NoAuth
    @Operation(summary = "邮箱密码登录")
    @PostMapping("/loginByEmail")
    public String loginByEmail(@RequestBody User user) {
        return userService.loginByEmail(user);

    }

    @Operation(summary = "当前在线人员")
    @GetMapping("/currentOnlineUsers")
    public List<User> currentOnlineUsers() {
        return userService.currentOnlineUsers();
    }

    @Operation(summary = "将用户踢下线")
    @GetMapping("/kickOut")
    public void kickOut(@RequestParam @NotBlank String userId) {
        userService.kickOut(userId);
    }


}
