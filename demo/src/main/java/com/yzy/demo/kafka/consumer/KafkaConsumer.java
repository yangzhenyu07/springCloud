package com.yzy.demo.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;


import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 消费者
 * */
@Component
public class KafkaConsumer {
    private static Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    ConsumerFactory consumerFactory;

    // 消费监听
    @KafkaListener(topics = {"topic1"},id = "consumer0",groupId = "felix-group",errorHandler = "consumerAwareErrorHandler")
    public void onMessage1(ConsumerRecord<?, ?> record, Acknowledgment ack) throws Exception {
        // 消费的哪个topic、partition的消息,打印出消息内容
            log.info("消费："+record.topic()+"-"+record.partition()+"-"+record.value());
            TimeUnit.SECONDS.sleep(1);
            ack.acknowledge();
    }

    /**
     * @Title 指定topic、partition、offset消费
     * 属性解释：
     * ① id：消费者ID；
     * ② groupId：消费组ID；
     * ③ topics：监听的topic，可监听多个；
     * */
    @KafkaListener(id = "consumer1",groupId = "felix-group",topics = {"topic2"},containerFactory = "filterContainerFactory",errorHandler = "consumerAwareErrorHandler")
    public void onMessage3(ConsumerRecord<?, ?> record, Acknowledgment ack) throws Exception {
//        System.out.println(">>>批量消费一次，records.size():" + records.size());
//        for (ConsumerRecord<?, ?> record : records) {
//            System.out.println(record.value());
//        }
            log.info("消费："+record.topic()+"-"+record.partition()+"-"+record.value());
            ack.acknowledge();


    }

    // 新建一个异常处理器，用@Bean注入
    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
        return (message, exception, consumer) -> {
            log.error(exception.getCause()+"消费信息："+message.getPayload());
            log.error("异常:"+exception.getMessage());
            return message;
        };
    }



    // 消息过滤器
    @Bean
    public ConcurrentKafkaListenerContainerFactory filterContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        // 消息过滤策略
        factory.setRecordFilterStrategy(consumerRecord -> {
            if (Integer.parseInt(consumerRecord.value().toString()) % 2 == 0) {
                return false;
            }
            //返回true消息则被过滤
            return true;
        });
        return factory;
    }

}
