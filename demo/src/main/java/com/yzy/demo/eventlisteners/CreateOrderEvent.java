package com.yzy.demo.eventlisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * spring 事件监听 应用事件类
 * @author yangzhenyu
 * */
@Component
public class CreateOrderEvent {
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 订单生产时，触发事件监听
     * */
    public void createEnent(String value){
        applicationContext.publishEvent(new OrderEvent(this,value));
    }
}
