package com.channelblab.springrain.common.disruptor;

/**
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2023/6/20 15:16
 * @description：
 * @modified By：
 */
public class EventFactory implements com.lmax.disruptor.EventFactory<Event> {
    @Override
    public Event newInstance() {
        return new Event();
    }
}
