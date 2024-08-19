package com.channelblab.springrain.common.disruptor;

import com.channelblab.springrain.dao.LogDao;
import com.channelblab.springrain.model.Log;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author ：dengyi(A.K.A Bear)
 * @date ：Created in 2023/6/20 15:18
 * @description：
 * @modified By：
 */
@Slf4j
@Component
public class MessageEventHandler implements EventHandler<MessageEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageEventHandler.class);

    private ApplicationContext context;


    public MessageEventHandler(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }


    @Override
    public void onEvent(MessageEvent event, long sequence, boolean endOfBatch) throws Exception {
        MessageEventType messageEventType = event.getMessageEventType();
        Object data = event.getData();

        switch (messageEventType) {
            case LOG:
                //log insert
                LogDao logdao = context.getBean(LogDao.class);
                logdao.insert((Log) data);
                break;
            default:
                LOGGER.error("not supported message type in internal message queue");
        }

    }
}