package com.yzy.demo.eventlisteners;

import com.yzy.demo.config.ShutDownListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * spring  事件监听
 * @author  yangzhenyu
 * */
@Component
public class MessageListener implements SmartApplicationListener {
    private static Logger log = LoggerFactory.getLogger(ShutDownListener.class);
    public MessageListener() {
        log.info("===================spring 事件监听配置启动===================");
    }
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        OrderEvent orderEvent =(OrderEvent) applicationEvent;
        log.info("=============spring  事件监听成功，监听到信息【{}】===========",orderEvent.getValue());
    }
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        //只有OrderEvent事件类型一致，才会监听事件
        return OrderEvent.class == aClass;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        //只有CreateOrderEvent发布事件时，才会监听事件
        return CreateOrderEvent.class == sourceType;
    }

    @Override
    public int getOrder() {
        //order ，数字越小，优先级越高，越先执行
        return 100;
    }


}
