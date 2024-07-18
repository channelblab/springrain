package com.channelblab.springrain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.channelblab.springrain.common.anotations.NoLogin;
import com.channelblab.springrain.model.Log;
import com.channelblab.springrain.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:12
 * @description：log controller
 * @modified By：
 */
@Api(tags = "操作日志接口")
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

//    @NoLogin
//    @ApiOperation("分页查询")
//    @GetMapping("/page")
//    public IPage<Log> page(@RequestParam(defaultValue = "1") Integer page,
//                           @RequestParam(defaultValue = "10") Integer size,
//                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDateTime,
//                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDateTime) {
//        return logService.page(page, size, startDateTime, endDateTime);
//    }

    @NoLogin
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public IPage<Log> page(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer size) {
        return logService.page(page, size, null, null);
    }


}
