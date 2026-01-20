package com.channelblab.springrain.common.utils;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2026-01-16 09:51
 * @description：
 * @modified By：
 */
public class SSELogUtil {
    private static final Map<String, SseEmitter> EMITTERS_MAP = new ConcurrentHashMap<>();
    private static final PatternLayout PATTERN_LAYOUT = new PatternLayout();

    public static void init() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        PATTERN_LAYOUT.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%xEx%n");
        PATTERN_LAYOUT.setContext(context);
        PATTERN_LAYOUT.start();
        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);
        SseAppender appender = new SseAppender();
        appender.setContext(context);
        appender.setName("SSE_LOG_APPENDER");
        appender.start();
        rootLogger.addAppender(appender);
    }

    public static SseEmitter registerEmitter(String userId) {
        EMITTERS_MAP.remove(userId);
        SseEmitter emitter = new SseEmitter(0L);
        EMITTERS_MAP.put(userId, emitter);
        emitter.onCompletion(() -> EMITTERS_MAP.remove(userId));
        emitter.onTimeout(() -> EMITTERS_MAP.remove(userId));
        return emitter;
    }


    private static class SseAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
        @Override
        protected void append(ILoggingEvent event) {
            //            String formattedMessage = event.getFormattedMessage();
            String formattedMessage = PATTERN_LAYOUT.doLayout(event);
            EMITTERS_MAP.forEach((userId, emitter) -> {
                try {
                    emitter.send(formattedMessage);
                } catch (IOException e) {
                    emitter.complete();
                    EMITTERS_MAP.remove(userId);
                }
            });
        }
    }

}