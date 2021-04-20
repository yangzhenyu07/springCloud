package com.yzy.demo.eventdrive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzy.demo.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * spring 事件驱动模型--生产者
 * @author yangzhenyu
 * */
@Component
public class LoginProducer {
    @Autowired
    private ObjectMapper objectMapper;
    private static Logger log = LoggerFactory.getLogger(LoginProducer.class);
    /**
     * 定义 发送消息的组件
     * */
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    public LoginProducer() {
        log.info("===================事件驱动模型--生产者注入ioc===================");
    }

    /**
     * 发送消息
     * */
    public void sendMsg(String name) throws JsonProcessingException {
        LoginEvent loginEvent = new LoginEvent(this,name, DateUtils.date2LongStr(new Date()),"127.0.0.1");
        log.info("spring 事件驱动模型-发送消息:{}",objectMapper.writeValueAsString(loginEvent));
        applicationEventPublisher.publishEvent(loginEvent);
    }


}
