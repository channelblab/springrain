package com.channelblab.springrain.common.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2023/6/20 15:16
 * @description：
 * @modified By：
 */
public class MessageEventFactory implements EventFactory<MessageEvent> {
    @Override
    public MessageEvent newInstance() {
        return new MessageEvent();
    }
}
