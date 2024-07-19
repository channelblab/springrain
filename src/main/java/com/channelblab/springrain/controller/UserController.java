package com.channelblab.springrain.controller;

import com.channelblab.springrain.model.Permission;
import com.channelblab.springrain.model.User;
import com.channelblab.springrain.service.LogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:12
 * @description：log controller
 * @modified By：
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private LogService logService;

//    @GetMapping("/page")
//    public IPage<Log> page(Integer page, Integer size,
//                           LocalDateTime startDateTime,
//                           LocalDateTime endDateTime) {
//        return logService.page(userId, page, size, startDateTime, endDateTime);
//    }


    @GetMapping("/userPermission")
    public List<Permission> userPermission() {
        return null;

    }

    @ApiOperation("用户名密码登录")
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return null;

    }
}
