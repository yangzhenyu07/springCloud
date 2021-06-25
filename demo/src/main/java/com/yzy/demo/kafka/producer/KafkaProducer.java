package com.yzy.demo.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * 生产者
 * */
@Component
public class KafkaProducer {
    private static Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    /**
     * 简单-生产者发起消息
     * */
    public void send(String topic,String normalMessage){
        kafkaTemplate.send(topic, normalMessage);
    }


    /**
     * 返回结果-生产者发起消息 方法1
     * */
    public  void returnSendOne(String topic,String normalMessage){
        kafkaTemplate.send(topic, normalMessage).addCallback(success -> {
            // 消息发送到的topic
            String topicMsg = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            log.info("发送消息成功:" + topicMsg + "-" + partition + "-" + offset);
        }, failure -> {
            log.error("发送消息失败:" + failure.getMessage());
        });
    }

    /**
     * 返回结果-生产者发起消息 方法2
     * */
    public  void returnSendTwo(String topic,String normalMessage){
        kafkaTemplate.send(topic, normalMessage).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送消息失败："+ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                        + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
            }
        });

    }

//    /**
//     * 声明事务提交
//     * */
//    public void sendTransactional(String topic,String normalMessage){
//         // 声明事务：后面报错消息不会发出去
//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send(topic,normalMessage).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
//                @Override
//                public void onFailure(Throwable ex) {
//                    System.out.println("发送消息失败："+ex.getMessage());
//                }
//
//                @Override
//                public void onSuccess(SendResult<String, Object> result) {
//                    System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
//                            + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
//                }
//            });
//            throw new RuntimeException("fail");
//        });
//
//    }




}
