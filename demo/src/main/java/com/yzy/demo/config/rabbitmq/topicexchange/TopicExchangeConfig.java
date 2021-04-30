package com.yzy.demo.config.rabbitmq.topicexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 基于TopicExchange的消息模型实战
 * @author yangzhenyu
 * */
@Configuration
public class TopicExchangeConfig {
    private static Logger log = LoggerFactory.getLogger(TopicExchangeConfig.class);
    public TopicExchangeConfig(){
        log.info("\"===================订阅消息模型-TopicExchange注入IOC===================");
    }
    @Autowired
    private Environment environment;
    /**
     * 创建广播消息模型-FanoutExchange
     * */
    //队列
    //创建队列
    @Bean(name = "topicQueueOne")
    public Queue topicQueueOne(){
        return new Queue(environment.getProperty("mq.yzy.info.topicqueue.name"),true);
    }
    @Bean(name = "topicQueueTwo")
    public Queue topicQueueTwo(){
        return new Queue(environment.getProperty("mq.yzy.info.topicqueue.name1"),true);
    }

    //创建交换机
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(environment.getProperty("mq.yzy.info.topicexchange.name"),true,false);
    }
    //创建绑定--->>>>通配符为*的路由
    @Bean
    public Binding topicBindingOne(){
        return  BindingBuilder.bind(topicQueueOne()).to(topicExchange()).with(environment.getProperty("mq.yzy.info.topicrouting.key.name"));
    }
    //创建绑定--->>>>通配符为#的路由
    @Bean
    public Binding topicBindingTwo(){
        return  BindingBuilder.bind(topicQueueTwo()).to(topicExchange()).with(environment.getProperty("mq.yzy.info.topicrouting.key.name1"));
    }
}
