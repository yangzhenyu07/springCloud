package com.yzy.demo.config.rabbitmq.dead;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列消息模型构建
 * @author yangzhenyu
 * */
@Configuration
public class DeadExchangeConfig {
    private static Logger log = LoggerFactory.getLogger(DeadExchangeConfig.class);
    public DeadExchangeConfig() {
        log.info("=================== 死信队列消息模型构建注入IOC===================");
    }
    @Autowired
    private Environment environment;
    //死信路由
    private final static String DEAD_ROUTING="mq.yzy.info.deadrouting.key.name";
    //死信交换机
    private final static String DEAD_EXCHANGE = "mq.yzy.info.deadexchange.name";
    //死信队列
    private final static String DEAD_QUEUE = "mq.yzy.info.deadqueue.name";
    //死信模型->基本模型->基本交换机(面向生产者)
    private final static String DEAD_EXCHANGE_PRODUCER="mq.yzy.info.deadexchange.producer.name";
    //死信模型->基本模型->基本路由(面向生产者)
    private final static String DEAD_ROUTING_PRODUCER="mq.yzy.info.deadrouting.producer.key.name";
    //真正的队列 -> 面向消费者
    private final static String REAL_QUEUE="mq.yzy.info.manualqueue.name";

    /**
     * 创建死信队列
     * */
    @Bean
    public Queue basicDeadQueue(){
        //创建死信队列的组成成分map,用来存放组成成员的相关成员
        Map<String,Object> args = new HashMap<>(3);
        //创建死信交换机
        args.put("x-dead-letter-exchange",environment.getProperty(DEAD_EXCHANGE));
        //创建死信路由
        args.put("x-dead-letter-routing-key",environment.getProperty(DEAD_ROUTING));
        //设定 TTL ,单位是毫秒，在这里指的是60s
        args.put("x-message-ttl",60000);
        //创建并返回死信队列实例
        return new Queue(environment.getProperty(DEAD_QUEUE),true,false,false,args);
    }

    //创建基本交换机 ---> 面向生产者
    @Bean
    public TopicExchange basicProducerExchange(){
        return new TopicExchange(environment.getProperty(DEAD_EXCHANGE_PRODUCER),true,false);
    }

    //创建基本绑定 --->基本交换机+基本路由 ---> 面向生产者
    @Bean
    public Binding basicProducerBinding(){
        return BindingBuilder.bind(basicDeadQueue()).to(basicProducerExchange()).with(environment.getProperty(DEAD_ROUTING_PRODUCER));
    }
    //====================================================================================



    //创建死信交换机
    @Bean
    public TopicExchange basicDeadExchange(){
        //创建并返回死信交换机实例
        return new TopicExchange(environment.getProperty(DEAD_EXCHANGE),true,false);
    }

    //创建死信路由及其绑定
    /**
     * @param manualQueue 真正的队列
     * */
    @Bean
    public Binding basicDeadBindingOne(@Qualifier("manualQueueOne") Queue manualQueue){
        return BindingBuilder.bind(manualQueue).to(basicDeadExchange()).with(environment.getProperty(DEAD_ROUTING));
    }

}
