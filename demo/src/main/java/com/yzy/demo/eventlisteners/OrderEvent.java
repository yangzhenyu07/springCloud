package com.yzy.demo.eventlisteners;

import org.springframework.context.ApplicationEvent;

/**
 * spring 事件监听 触发事件类
 * @author yangzhenyu
 * */
public class OrderEvent extends ApplicationEvent {
    private String value;
    /**
     * 触发事件方法
     * */
    public OrderEvent(Object source,String value) {
        super(source);
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}
