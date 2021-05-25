package com.yzy.demo.config.rabbitmq.manual;

import com.yzy.demo.rabbitmq.manual.consumer.ManualConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 基于手动确认消费模式实战配置
 * @author yangzhenyu
 * */
@Configuration
public class ManualMqConfig {
    private static Logger log = LoggerFactory.getLogger(ManualMqConfig.class);
    public ManualMqConfig() {
        log.info("=================== 基于手动确认消费模式实战配置注入IOC===================");
    }
    @Autowired
    private Environment environment;
    //自动装配 RabbitMQ 的连接工厂实例
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    //消费者
    @Autowired
    private ManualConsumer manualConsumer;

    //创建队列
    @Bean(name = "manualQueueOne")
    public Queue manualQueueOne(){
        return new Queue(environment.getProperty("mq.yzy.info.manualqueue.name"),true);
    }

    //交换机
    @Bean
    public DirectExchange manualExchange(){
        return new DirectExchange(environment.getProperty("mq.yzy.info.manualexchange.name"),true,false);
    }

    //创建绑定
    //directQueueOne
    @Bean
    public Binding basicBindingOne(){
        return BindingBuilder.bind(manualQueueOne()).to(manualExchange()).with(environment.getProperty("mq.yzy.info.manualrouting.key.name"));
    }

    /**
     * 基于手动确认消费模式实战配置
     * */
    @Bean(name = "manualListenerContainer")
    public SimpleMessageListenerContainer manualListenerContainer(@Qualifier("manualQueueOne") Queue manualQueue){
        //自定义消息监听器所在的容器工厂
        SimpleMessageListenerContainer factory = new SimpleMessageListenerContainer();
        //设置容器工厂所用的实例
        factory.setConnectionFactory(cachingConnectionFactory);
        //设置消息的确认消费模式，在这里为MANUAL,表示手动确认消费
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        //设置并发消费者实例的初始值
        factory.setConcurrentConsumers(1);
        //设置并发消费者实例的最大数量
        factory.setMaxConcurrentConsumers(1);
        //设置并发消费者实例中每个实例拉取的消息数量
        factory.setPrefetchCount(1);

        //指定该容器监听的队列
        factory.setQueues(manualQueue);
        //指定该容器中的消费监听器 即消费者
        factory.setMessageListener(manualConsumer);
        return factory;
    }
}
