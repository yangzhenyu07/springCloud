package com.yzy.demo.config.rabbitmq.fanoutexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 基于FanouExchange的消息模型实战
 * @author yangzhenyu
 * */
@Configuration
public class FanoutExchangeCofig {
    private static Logger log = LoggerFactory.getLogger(FanoutExchangeCofig.class);
    public FanoutExchangeCofig(){
        log.info("\"===================广播消息模型-FanoutExchange注入IOC===================");
    }
    @Autowired
    private Environment environment;
    /**
     * 创建广播消息模型-FanoutExchange
     * */
    //队列
    @Bean(name = "fanoutQueueOne")
    public Queue fanoutQueueOne(){
        return new Queue(environment.getProperty("mq.yzy.info.fanoutqueue.name"),true);
    }

    @Bean(name = "fanoutQueueTwo")
    public Queue fanoutQueueTwo(){
        return new Queue(environment.getProperty("mq.yzy.info.fanoutqueue.name1"),true);
    }

    //创建交换机-FanoutExchange
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(environment.getProperty("mq.yzy.info.fanoutExchange.name"),true,false);
    }

    //创建绑定1
    @Bean
    public Binding fanoutBindingOne(){
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }
    //创建绑定2
    @Bean
    public Binding fanoutBindingTwo(){
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }
}
