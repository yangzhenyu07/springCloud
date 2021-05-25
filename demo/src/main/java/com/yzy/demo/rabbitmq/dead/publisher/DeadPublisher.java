package com.yzy.demo.rabbitmq.dead.publisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzy.demo.test.vo.Student;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 死信队列消息模型构建---生产者
 * @author yangzhenyu
 * */
@Component
public class DeadPublisher {
    private static Logger log = LoggerFactory.getLogger(DeadPublisher.class);
    //序列化和返序列化
    @Autowired
    private ObjectMapper objectMapper;
    //定义RabbitMQ 组件
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //定义环境变量读取实例
    @Autowired
    private Environment env;

    //死信模型->基本模型->基本交换机(面向生产者)
    private final static String DEAD_EXCHANGE_PRODUCER="mq.yzy.info.deadexchange.producer.name";
    //死信模型->基本模型->基本路由(面向生产者)
    private final static String DEAD_ROUTING_PRODUCER="mq.yzy.info.deadrouting.producer.key.name";

    /**
     * 发送对象类型的消息给死信队列
     * @param info
     * */
    public void sendMsg(Student info){
        try{
            if (info != null){
                //定义消息传输的格式为json
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定消息模型中的交换机
                rabbitTemplate.setExchange(env.getProperty(DEAD_EXCHANGE_PRODUCER));
                //将字符串值转换成二进制的数据流
                Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(info))
                        .build();
                rabbitTemplate.convertAndSend(env.getProperty(DEAD_ROUTING_PRODUCER),msg, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        //获取消息的属性
                        MessageProperties messageProperties = message.getMessageProperties();
                        //设置消息的持久化模式
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        //设置消息头，即指定发送消息的所属对象类型
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Student.class);
                        //设置消息的TTL,当消息和队列同时设置TTL时，取最短 10s
                        messageProperties.setExpiration(String.valueOf(10000));
                        return message;
                    }
                });
                log.info("死信队列消息模型 -生产者发出消息:{}",objectMapper.writeValueAsString(info));
            }
        }catch (Exception e){
            log.error("死信队列消息模型 -生产者发出消息-发生异常:{}",info,e.fillInStackTrace());
        }
    }
}
