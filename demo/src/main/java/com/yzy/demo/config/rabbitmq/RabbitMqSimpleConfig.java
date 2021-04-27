package com.yzy.demo.config.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 简单消息模型配置-字节流配置
 * @author yangzhenyu
 * */
@Configuration
public class RabbitMqSimpleConfig {
    private static Logger log = LoggerFactory.getLogger(RabbitMqSimpleConfig.class);
    public RabbitMqSimpleConfig() {
        log.info("=================== 简单消息模型配置-字节流配置注入IOC===================");
    }

    @Autowired
    private Environment environment;
    /**
     * 创建简单消息模型:队列、交换机、路由
     * */
    //创建队列
    @Bean(name = "basicQueue")
    public Queue basicQueue(){
        return new Queue(environment.getProperty("mq.yzy.info.queue.name"),true);
    }
    /**
     * 以DirectExchange为例
     * */
    //交换机
    @Bean
    public DirectExchange basicExchange(){
        return new DirectExchange(environment.getProperty("mq.yzy.info.exchange.name"),true,false);
    }

    //创建绑定
    @Bean
    public Binding basicBinding(){
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(environment.getProperty("mq.yzy.info.routing.key.name"));
    }
}
