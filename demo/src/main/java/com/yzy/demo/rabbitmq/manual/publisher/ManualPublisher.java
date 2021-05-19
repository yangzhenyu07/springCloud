package com.yzy.demo.rabbitmq.manual.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzy.demo.test.vo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 认为手动确认消费-生产者-字节流模式
 * @author yangzhenyu
 * */
@Component
public class ManualPublisher {
    private static Logger log = LoggerFactory.getLogger(ManualPublisher.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    //定义环境变量读取实例
    @Autowired
    private Environment env;
    //序列化和返序列化
    @Autowired
    private ObjectMapper objectMapper;

    //交换机
    private final static String EXCHANGE="mq.yzy.info.manualexchange.name";
    //路由
    private final static String ROUTING_ONE="mq.yzy.info.manualrouting.key.name";

    /**
     * 基于MANUAL 机制 -生产者发出消息
     * */
    public void sendAutoMsg(Student student){
        try{
            if (student != null){
                //定义消息传输的格式为json
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定消息模型中的交换机
                rabbitTemplate.setExchange(env.getProperty(EXCHANGE));
                //将字符串值转换成二进制的数据流
                Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(student))
                              .build();
                rabbitTemplate.convertAndSend(env.getProperty(ROUTING_ONE),msg, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        //获取消息的属性
                        MessageProperties messageProperties = message.getMessageProperties();
                        //设置消息的持久化模式
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        return message;
                    }
                });
                log.info("基于MANUAL 机制 -生产者发出消息:{}",objectMapper.writeValueAsString(student));
            }
        }catch (Exception e){
            log.error("基于MANUAL 机制 -生产者发出消息-发生异常:{}",student,e.fillInStackTrace());
        }


    }
}
