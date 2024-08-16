package com.channelblab.springrain.common.config;

import com.channelblab.springrain.common.disruptor.MessageEvent;
import com.channelblab.springrain.common.disruptor.MessageEventFactory;
import com.channelblab.springrain.common.disruptor.MessageEventHandler;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-16 16:57
 * @description：
 * @modified By：
 */
@Configuration
public class DisruptorConfig {

    @Bean
    public Disruptor<MessageEvent> disruptor(ApplicationContext context) {
        MessageEventFactory factory = new MessageEventFactory();
        int bufferSize = 1024;
        ExecutorService executor = Executors.newCachedThreadPool();
        Disruptor<MessageEvent> disruptor = new Disruptor<>(factory, bufferSize, executor, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(new MessageEventHandler(context));
        disruptor.start();
        return disruptor;
    }

}
