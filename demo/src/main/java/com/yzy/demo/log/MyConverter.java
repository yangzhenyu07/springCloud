package com.yzy.demo.log;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 自定义消息处理器
 * @author yangzhenyu
 * */
public class MyConverter extends MessageConverter {


    public String convert(ILoggingEvent event) {
        return super.convert(event);
    }
}
