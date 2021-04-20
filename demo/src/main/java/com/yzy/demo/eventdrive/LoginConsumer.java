package com.yzy.demo.eventdrive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzy.demo.config.ShutDownListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * spring 事件驱动模型--消费者
 * @author yangzhenyu
 * */
@Component //加入spring 容器
@EnableAsync //允许异步执行
public class LoginConsumer implements ApplicationListener<LoginEvent> {

    private static Logger log = LoggerFactory.getLogger(LoginConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;
    public LoginConsumer() {
        log.info("===================事件驱动模型--消费者注入ioc===================");
    }

    /**
     * 监听消费者消息
     * */
    @Override
    @Async
    public void onApplicationEvent(LoginEvent loginEvent) {
        try {
            log.info("spring 事件驱动模型-接受消息:{}",objectMapper.writeValueAsString(loginEvent));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
