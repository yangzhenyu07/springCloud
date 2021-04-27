package com.yzy.demo.rabbitmq.test.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzy.demo.test.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 基本消费模型-生产者-对象类型
 * @author yangzhenyu
 * */
@Component
public class SimpleObjectPublisher {
    private static Logger log = LoggerFactory.getLogger(SimpleObjectPublisher.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //定义环境变量读取实例
    @Autowired
    private Environment env;
    //序列化和返序列化
    @Autowired
    private ObjectMapper objectMapper;
    //交换机
    private final static String EXCHANGE="mq.yzy.info.exchange.name1";
    //路由
    private final static String ROUTING="mq.yzy.info.routing.key.name1";


    /**
     * 发送消息
     * @param user 待发送消息  即对象
     * */
    public void sendObjectMsg(User user) throws JsonProcessingException {
        //设置一个假的id
        String uuid = UUID.randomUUID().toString().replace("-", "");
        if (user!=null){
            try {
                //定义消息传输的格式为json
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定消息模型中的交换机
                rabbitTemplate.setExchange(env.getProperty(EXCHANGE));
                //转化并发送
                /**
                 * 通过MessagePostProcessor的实现，指定发送消息的类型
                 * */
                rabbitTemplate.convertAndSend(env.getProperty(ROUTING),user, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        //获取消息的属性
                        MessageProperties messageProperties = message.getMessageProperties();
                        //设置消息的持久化模式
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        //设置消息的类型
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,User.class);
                        return message;
                    }
                },new CorrelationData(uuid));
                log.info("【基本消息模型-生产者-发送消息】:{}",objectMapper.writeValueAsString(user));
            }catch (Exception e){
                log.error("【基本消息模型-生产者-发送消息】失败:{}",objectMapper.writeValueAsString(user),e);
            }
        }else {
            log.warn("对象为null");
        }
    }
}
