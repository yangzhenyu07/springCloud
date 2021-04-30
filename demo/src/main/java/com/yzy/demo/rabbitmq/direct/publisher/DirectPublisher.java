package com.yzy.demo.rabbitmq.direct.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 直连消费模型-生产者-字节流模式
 * @author yangzhenyu
 * */
@Component
public class DirectPublisher {
    private static Logger log = LoggerFactory.getLogger(DirectPublisher.class);
    //序列化和返序列化
    @Autowired
    private ObjectMapper objectMapper;
    //定义RabbitMQ 组件
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //定义环境变量读取实例
    @Autowired
    private Environment env;
    //交换机
    private final static String EXCHANGE="mq.yzy.info.directexchange.name";
    //路由1
    private final static String ROUTING_ONE="mq.yzy.info.directrouting.key.name";
    //路由2
    private final static String ROUTING_TWO="mq.yzy.info.directrouting.key.name1";
    /**
     * 发送消息
     * @param message 待发送消息  即一串字符串值
     * */
    public void sendMsgOne(String message){
        //设置一个假的id
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //判断某字符串是否为空或长度为0或由空白符(whitespace)构成
        if (StringUtils.isNotBlank(message)){
            try {
                //定义消息传输的格式为json
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定消息模型中的交换机
                rabbitTemplate.setExchange(env.getProperty(EXCHANGE));
                //将字符串值转换成二进制的数据流
                Message msg = MessageBuilder.withBody(message.getBytes("UTF-8")).build();
                //转化并发送
                CorrelationData correlationData = new CorrelationData(uuid);
                correlationData.setReturnedMessage(msg);
                rabbitTemplate.convertAndSend(env.getProperty(ROUTING_ONE),msg.getBody(),correlationData);
                log.info("【直连消费模型-生产者one-发送消息】:{}",message);
            }catch (Exception e){
                log.error("【直连消费模型-生产者one-发送消息】失败:{}",message,e);
            }
        }else{
            log.warn("字符串为空或长度为0或由空白符(whitespace)构成");
        }
    }
    /**
     * 发送消息
     * @param message 待发送消息  即一串字符串值
     * */
    public void sendMsgTwo(String message){
        //设置一个假的id
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //判断某字符串是否为空或长度为0或由空白符(whitespace)构成
        if (StringUtils.isNotBlank(message)){
            try {
                //定义消息传输的格式为json
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定消息模型中的交换机
                rabbitTemplate.setExchange(env.getProperty(EXCHANGE));
                //将字符串值转换成二进制的数据流
                Message msg = MessageBuilder.withBody(message.getBytes("UTF-8")).build();
                //转化并发送
                CorrelationData correlationData = new CorrelationData(uuid);
                correlationData.setReturnedMessage(msg);
                rabbitTemplate.convertAndSend(env.getProperty(ROUTING_TWO),msg.getBody(),correlationData);
                log.info("【直连消费模型-生产者two-发送消息】:{}",message);
            }catch (Exception e){
                log.error("【直连消费模型-生产者two-发送消息】失败:{}",message,e);
            }
        }else{
            log.warn("字符串为空或长度为0或由空白符(whitespace)构成");
        }
    }
}
