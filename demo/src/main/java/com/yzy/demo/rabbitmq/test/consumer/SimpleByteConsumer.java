package com.yzy.demo.rabbitmq.test.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 基本消费模型-消费者-字节流模式
 * @author yangzhenyu
 * */
@Component
public class SimpleByteConsumer {
    private static Logger log = LoggerFactory.getLogger(SimpleByteConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 监听并接受消费者队列中的信息-单一容器工厂模型
     * 使用 @Payload 和 @Headers 注解可以消息中的 body 与 headers 信息
     * @param msg 接受的字节流
     * */
    @RabbitListener(queues = "${mq.yzy.info.queue.name}",containerFactory = "simpleListenerContainer")
    public void consumeMsg(@Payload byte[] msg, @Headers Map<String,Object> headers){
        try{
            String message = new String(msg,"UTF-8");
            log.info("【基本消息模型-消费者-接受消息】监听到消息:{}",message);
        }catch (Exception e){
            log.error("【基本消息模型-消费者-接受消息】:出现异常",e.fillInStackTrace());
        }
    }
}
