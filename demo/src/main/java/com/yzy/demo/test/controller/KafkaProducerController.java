package com.yzy.demo.test.controller;

import com.yzy.demo.kafka.producer.KafkaProducer;
import com.yzy.demo.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Kafka 练习
 * @author yangzhenyu
 * */
@Api(value = "Kafka 练习", tags = {"Kafka 练习"})
@RestController
@RequestMapping(value="/api/kafka")
public class KafkaProducerController {
    private static final String TOPIC = "topic1"; //主题
    private static final String TOPIC2 = "topic2"; //主题

    @Autowired
    private KafkaProducer kafkaProducer;

    /**
     * Kafka 练习 -简单-生产者发起消息
     *
     */
    @ApiOperation(value="Kafka 练习 -简单-生产者发起消息", notes="Kafka 练习-简单-生产者发起消息")
    @GetMapping("/send/{value}")
    public ResponseBo send(@PathVariable  String value){
        try{
            kafkaProducer.send(TOPIC,value);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBo.error();
        }
         return ResponseBo.ok();
    }

    /**
     * Kafka 练习 -返回结果-生产者发起消息 方法1
     *
     */
    @ApiOperation(value="Kafka 练习 -返回结果-生产者发起消息 方法1", notes="Kafka 练习-返回结果-生产者发起消息 方法1")
    @GetMapping("/returnSendOne/{value}")
    public ResponseBo returnSendOne(@PathVariable  String value){
        try{
            kafkaProducer.returnSendOne(TOPIC2,value);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBo.error();
        }
        return ResponseBo.ok();
    }

    /**
     * Kafka 练习 -返回结果-生产者发起消息 方法2
     *
     */
    @ApiOperation(value="Kafka 练习 -返回结果-生产者发起消息 方法2", notes="Kafka 练习-返回结果-生产者发起消息 方法2")
    @GetMapping("/returnSendTwo/{value}")
    public ResponseBo returnSendTwo(@PathVariable  String value){
        try{
            kafkaProducer.returnSendTwo(TOPIC,value);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBo.error();
        }
        return ResponseBo.ok();
    }

//    /**
//     * Kafka 练习 -声明事务提交-生产者发起消息
//     *
//     */
//    @ApiOperation(value="Kafka 练习 -声明事务提交-生产者发起消息", notes="Kafka 练习-声明事务提交-生产者发起消息")
//    @GetMapping("/sendTransactional/{value}")
//    public ResponseBo sendTransactional(@PathVariable  String value){
//        try{
//            kafkaProducer.sendTransactional(TOPIC,value);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseBo.error();
//        }
//        return ResponseBo.ok();
//    }
    /**
     * Kafka 练习 -声明事务提交-生产者发起消息
     *
     */
    @ApiOperation(value="Kafka 练习 -批量提交-生产者发起消息", notes="Kafka 练习-批量提交-生产者发起消息")
    @GetMapping("/sendTransactionalBatch")
    public ResponseBo sendTransactionalBatch(){
        try{
            for (int i=0;i<100;i++){
                kafkaProducer.returnSendTwo(TOPIC2,i+"");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBo.error();
        }
        return ResponseBo.ok();
    }

}
