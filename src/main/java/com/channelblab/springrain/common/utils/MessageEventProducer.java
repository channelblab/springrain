package com.channelblab.springrain.common.utils;

import com.channelblab.springrain.common.disruptor.MessageEvent;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageEventProducer {
    @Autowired
    private Disruptor<MessageEvent> disruptor;

    public void produce(MessageEvent messageEvent) {
        RingBuffer<MessageEvent> ringBuffer = disruptor.getRingBuffer();

        long sequence = ringBuffer.next();
        try {
            MessageEvent event = ringBuffer.get(sequence);
            event.setMessageEventType(messageEvent.getMessageEventType());
            event.setData(messageEvent.getData());
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
