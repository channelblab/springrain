package com.channelblab.springrain.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.channelblab.springrain.common.anotations.NoLog;
import com.channelblab.springrain.common.anotations.NoResponseHandle;
import com.channelblab.springrain.common.holder.UserHolder;
import com.channelblab.springrain.common.utils.SSELogUtil;
import com.channelblab.springrain.model.SystemLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:12
 * @description：log controller
 * @modified By：
 */
@Tag(name = "Framework-System Log/框架-系统日志接口")
@RestController
@RequestMapping("/systemLog")
public class SystemLogController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemLogController.class);

    @Operation(summary = "Modify System Log Level/修改系统日志输出级别")
    @PostMapping("/changeLevel")
    public void changeLevel(@RequestBody @Validated SystemLogger systemLogger) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger rootlogger = context.getLogger(LOGGER.ROOT_LOGGER_NAME);
        rootlogger.setLevel(Level.toLevel(String.valueOf(systemLogger.getLevel())));
    }

    @Operation(summary = "Query Current Log Level/当前日志输出级别")
    @GetMapping("/currentLevel")
    public String currentLevel() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger rootlogger = context.getLogger(LOGGER.ROOT_LOGGER_NAME);
        return rootlogger.getLevel().levelStr;
    }

    @NoLog
    @NoResponseHandle
    @Operation(summary = "Realtime Log Output/日志实时输出")
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() throws IOException {
        return SSELogUtil.registerEmitter(UserHolder.getUser().getId());
    }


}