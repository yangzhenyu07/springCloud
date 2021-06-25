package com.yzy.demo.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 创建主题
 * */
@Component
public class KafkaInitialConfiguration {
    private static Logger log = LoggerFactory.getLogger(KafkaInitialConfiguration.class);
    public KafkaInitialConfiguration() {
        log.info("=================== kafka 主题配置bean相关组件注入IOC===================");
    }
    // 创建一个名为topic1的Topic并设置分区数为8，分区副本数为2
    @Bean
    public NewTopic initialTopic1() {
        return new NewTopic("topic1",8, (short) 2 );
    }

    // 如果要修改分区数，只需修改配置值重启项目即可
    // 修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
    @Bean
    public NewTopic updateTopic1() {
        return new NewTopic("topic1",10, (short) 2 );
    }

    // ========================================================批量测试主题==============================
    // 创建一个名为topic1的Topic并设置分区数为8，分区副本数为2
    @Bean
    public NewTopic initialTopicBatch1() {
        return new NewTopic("topic2",8, (short) 2 );
    }

    // 如果要修改分区数，只需修改配置值重启项目即可
    // 修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
    @Bean
    public NewTopic updateTopicBatch1() {
        return new NewTopic("topic2",10, (short) 2 );
    }

}
