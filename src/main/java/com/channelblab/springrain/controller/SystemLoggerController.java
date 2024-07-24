package com.channelblab.springrain.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.channelblab.springrain.model.SystemLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:12
 * @description：log controller
 * @modified By：
 */
@Tag(name = "框架-系统日志接口")
@RestController
@RequestMapping("/systemLogger")
public class SystemLoggerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemLoggerController.class);

    @Operation(summary = "修改系统日志输出级别")
    @PostMapping("/changeLevel")
    public void changeLevel(@RequestBody @Validated SystemLogger systemLogger) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger rootlogger = context.getLogger(LOGGER.ROOT_LOGGER_NAME);
        rootlogger.setLevel(Level.toLevel(String.valueOf(systemLogger.getLevel())));
    }

    @Operation(summary = "当前日志输出级别")
    @GetMapping("/currentLevel")
    public String currentLevel() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger rootlogger = context.getLogger(LOGGER.ROOT_LOGGER_NAME);
        return rootlogger.getLevel().levelStr;
    }


}
