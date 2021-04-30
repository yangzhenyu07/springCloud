package com.yzy.demo.config.rabbitmq.directexchange;

import com.yzy.demo.config.rabbitmq.RabbitMqSimObjectConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 基于DirectExchange的消息模型实战
 * @author yangzhenyu
 * */
@Configuration
public class DirectExchangeConfig {

    private static Logger log = LoggerFactory.getLogger(RabbitMqSimObjectConfig.class);
    public DirectExchangeConfig() {
        log.info("=================== 直连消息模型-DirectExchange配置注入IOC===================");
    }
    @Autowired
    private Environment environment;
    /**
     * 创建直连消息模型:队列、交换机、路由
     * */
    //创建队列
    @Bean(name = "directQueueOne")
    public Queue directQueueOne(){
        return new Queue(environment.getProperty("mq.yzy.info.directqueue.name"),true);
    }
    @Bean(name = "directQueueTwo")
    public Queue directQueueTwo(){
        return new Queue(environment.getProperty("mq.yzy.info.directqueue.name1"),true);
    }

    //交换机
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(environment.getProperty("mq.yzy.info.directexchange.name"),true,false);
    }

    //创建绑定
    //directQueueOne
    @Bean
    public Binding basicBindingOne(){
        return BindingBuilder.bind(directQueueOne()).to(directExchange()).with(environment.getProperty("mq.yzy.info.directrouting.key.name"));
    }

    //directQueueTwo
    @Bean
    public Binding basicBindingTwo(){
        return BindingBuilder.bind(directQueueTwo()).to(directExchange()).with(environment.getProperty("mq.yzy.info.directrouting.key.name1"));
    }
}
