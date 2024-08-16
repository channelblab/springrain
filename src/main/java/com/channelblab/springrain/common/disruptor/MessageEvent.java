package com.channelblab.springrain.common.disruptor;

import lombok.Data;

/**
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2023/6/20 15:14
 * @description：
 * @modified By：
 */
@Data
public class MessageEvent {
    private MessageEventType messageEventType;
    private Object data;

}
