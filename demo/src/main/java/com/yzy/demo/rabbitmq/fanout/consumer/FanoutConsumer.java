package com.yzy.demo.rabbitmq.fanout.consumer;

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
 * 广播消费模型-消费者-字节流模式
 * @author yangzhenyu
 * */
@Component
public class FanoutConsumer {
    private static Logger log = LoggerFactory.getLogger(FanoutConsumer.class);
    //序列化和返序列化
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 监听广播消息模型中的队列fanoutQueueOne
     * 使用 @Payload 和 @Headers 注解可以消息中的 body 与 headers 信息
     * */
    @RabbitListener(queues = "${mq.yzy.info.fanoutqueue.name}",containerFactory = "multiListenerContainer")
    public void consumerFanoutQueueOne(@Payload byte[] msg, @Headers Map<String,Object> headers){
        try{
            String message = new String(msg,"UTF-8");
            log.info(">>>>>>>队列one>>>【广播消息模型-消费者-接受消息】监听到消息:{}",message);
        }catch (Exception e){
            log.error(">>>>>>>队列one>>>【广播消息模型-消费者-接受消息】:出现异常",e.fillInStackTrace());
        }
    }

    /**
     * 监听广播消息模型中的队列fanoutQueueTwo
     * 使用 @Payload 和 @Headers 注解可以消息中的 body 与 headers 信息
     * */
    @RabbitListener(queues = "${mq.yzy.info.fanoutqueue.name1}",containerFactory = "multiListenerContainer")
    public void consumerFanoutQueueTwo(@Payload byte[] msg, @Headers Map<String,Object> headers){
        try{
            String message = new String(msg,"UTF-8");
            log.info(">>>>>>>队列two>>>【广播消息模型-消费者-接受消息】监听到消息:{}",message);
        }catch (Exception e){
            log.error(">>>>>>>队列two>>>【广播消息模型-消费者-接受消息】:出现异常",e.fillInStackTrace());
        }
    }

}
