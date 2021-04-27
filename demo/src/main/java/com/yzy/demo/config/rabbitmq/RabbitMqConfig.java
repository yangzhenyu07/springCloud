package com.yzy.demo.config.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * RabbitMq 自定义配置bean相关组件
 * @author yangzhenyu
 * */
@Component
@ConfigurationProperties(prefix = "config.rabbitmq.factory", ignoreUnknownFields = true)
public class RabbitMqConfig {
    private static Logger log = LoggerFactory.getLogger(RabbitMqConfig.class);
    public RabbitMqConfig() {
        log.info("=================== RabbitMq 自定义配置bean相关组件注入IOC===================");
    }

    /**
     * 设置并发消费者实例的初始值
     * */
    @Value("10")
    private Integer concurrentConsumers;
    /**
     * 设置并发消费者实例的最大数量
     * */
    @Value("15")
    private Integer maxConcurrentConsumers;
    /**
     * 设置并发消费者实例中每个实例拉取的消息数量
     * */
    @Value("10")
    private Integer prefetchCount;
    //==================================================================================================================
    //自动装配 RabbitMQ 的连接工厂实例
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    //自动装配消息监听器所在的容器工厂配置类实例
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
    //==================================================================================================================

    /**
     * 多个消费者实例配置，主要针对高并发业务场景配置
     * */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        //自定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂所用的实例
        factoryConfigurer.configure(factory,cachingConnectionFactory);
        //设置消息在传输中的格式，JSON格式
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置消息的确认消费模式，在这里为NONE,表示不需要确认消费
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        //设置并发消费者实例的初始值
        factory.setConcurrentConsumers(concurrentConsumers);
        //设置并发消费者实例的最大数量
        factory.setMaxConcurrentConsumers(maxConcurrentConsumers);
        //设置并发消费者实例中每个实例拉取的消息数量
        factory.setPrefetchCount(prefetchCount);
        return factory;
    }


    /**
     * 单一消费者实例配置
     * */
    @Bean(name = "simpleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        //自定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂所用的实例
        factory.setConnectionFactory(cachingConnectionFactory);
        //设置消息在传输中的格式，JSON格式
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置并发消费者实例的初始值
        factory.setConcurrentConsumers(1);
        //设置并发消费者实例的最大数量
        factory.setMaxConcurrentConsumers(1);
        //设置并发消费者实例中每个实例拉取的消息数量
        factory.setPrefetchCount(1);
        return factory;
    }

    /**
     * 自定义配置RabbitMq 发送消息的操作组件 RabbitTemplate
     * */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        //设置发送消息后进行确认
        cachingConnectionFactory.setPublisherConfirms(true);
        //设置发送信息后返回确认信息
        cachingConnectionFactory.setPublisherReturns(true);
        //构造发送消息组件实例对象
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMandatory(true);
        //发送消息后，如果发送成功，则输出"消息发送成功"的反馈信息
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送确认: 【correlationData】：【{}】，【ack】：【{}】，【cause】：【{}】",correlationData,ack,cause);
            }
        });
        //发送信息后，如果发送失败，则输出"消息发送失败-消息丢失"的反馈信息
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback(){
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.warn("消息丢失: 【exchange】：【{}】，【routingKey】：【{}】，【replyCode】：【{}】,【replyText】：【{}】",exchange,routingKey,replyCode,replyText);
                log.warn("消息丢失:【message】:{}",message);
            }
        });
        return  rabbitTemplate;
    }
}
