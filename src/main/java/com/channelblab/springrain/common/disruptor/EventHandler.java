package com.channelblab.springrain.common.disruptor;

import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;
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
public class EventHandler implements WorkHandler<Event> {
    private ApplicationContext applicationContext;

    public EventHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public void onEvent(Event event) throws Exception {
        log.info("event info is:{}", event);


        Object params = event.getParams();

        switch (event.getEventType()) {
            case LOG:


                break;

        }


    }
}
