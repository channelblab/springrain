package com.channelblab.springrain.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LogBroadcastService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter addEmitter(String userId) {
        SseEmitter emitter = new SseEmitter(0L); // 永不超时
        emitters.put(userId, emitter);

        // 连接断开或超时移除
        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));

        return emitter;
    }

    // 广播日志的方法
    public void broadcast(String logMessage) {
        emitters.forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().data(logMessage));
            } catch (IOException e) {
                // 发送失败说明连接断开，移除
                emitter.complete();
                emitters.remove(userId);
            }
        });
    }
}