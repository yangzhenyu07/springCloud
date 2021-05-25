package com.yzy.demo.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzy.demo.log.common.BaseLog;
import com.yzy.demo.rabbitmq.dead.publisher.DeadPublisher;
import com.yzy.demo.rabbitmq.direct.publisher.DirectPublisher;
import com.yzy.demo.rabbitmq.fanout.publisher.FanoutPublisher;
import com.yzy.demo.rabbitmq.manual.publisher.ManualPublisher;
import com.yzy.demo.rabbitmq.test.publisher.SimpleBytePublisher;
import com.yzy.demo.rabbitmq.test.publisher.SimpleObjectPublisher;
import com.yzy.demo.rabbitmq.topic.publisher.TopicPublisher;
import com.yzy.demo.test.vo.Student;
import com.yzy.demo.test.vo.Teacher;
import com.yzy.demo.test.vo.User;
import com.yzy.demo.test.vo.rabbitmq.RabbitMqMsgVo;
import com.yzy.demo.test.vo.rabbitmq.TopicPublisherVo;
import com.yzy.demo.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * RabbitMQ 练习
 * author : yangzhenyu
 * **/
@Api(value = "RabbitMQ 测试接口", tags = {"RabbitMQ 测试接口"})
@RestController
@RequestMapping("api/rabbitmq/")
public class RabbitMQController extends BaseLog {
    @Override
    public long init(String name, String param) {
        this.setClazz("RabbitMQController");
        return startLog(name,param);
    }
    //基本消费模型-生产者-字节流模式
    @Autowired
    private SimpleBytePublisher simpleBytePublisher;
    //基本消费模型-生产者-类型对象
    @Autowired
    private SimpleObjectPublisher simpleObjectPublisher;
    //直连消费模型-生产者
    @Autowired
    private DirectPublisher directPublisher;
    //广播消费模型-生产者
    @Autowired
    private FanoutPublisher fanoutPublisher;
    //订阅消费模型-生产者
    @Autowired
    private TopicPublisher topicPublisher;
    //基于manual 机制-人为手动确认消费-生产者
    @Autowired
    private ManualPublisher manualPublisher;
    //死信队列-延迟
    @Autowired
    private DeadPublisher deadPublisher;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 死信队列模型演示
     * */
    @ApiOperation(value = "死信队列模型演示",notes = "死信队列模型演示")
    @ResponseBody
    @PostMapping("/deadPublisher")
    public ResponseBo deadPublisher(@RequestBody @Valid Student vo) throws JsonProcessingException {
        String msgValue = "deadPublisher";
        long startTime = init(msgValue,objectMapper.writeValueAsString(vo));
        try{
            deadPublisher.sendMsg(vo);
            endLog(msgValue,startTime);
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
        }
        return ResponseBo.ok();
    }

    /**
     * 基本消费模型-生产者-字节流模式
     * */
    @ApiOperation(value = "基本消费模型-生产者-字节流模式演示",notes = "基本消费模型-生产者-字节流模式演示")
    @ResponseBody
    @PostMapping("/simpleBytePublisher")
    public ResponseBo simpleBytePublisher(@RequestBody @Valid RabbitMqMsgVo vo) throws JsonProcessingException {
        String msgValue = "simpleBytePublisher";
        long startTime = init(msgValue,objectMapper.writeValueAsString(vo));
        try{
            //注意:真实环境下要用事件异步驱动的方式发起消息
            simpleBytePublisher.sendMsg(vo.getMsg());
            endLog(msgValue,startTime);
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
        }
        return ResponseBo.ok();
    }

    /**
     * 基本消费模型-生产者-对象类型
     * */
    @ApiOperation(value = "基本消费模型-生产者-对象类型模式演示",notes = "基本消费模型-生产者-对象类型模式演示")
    @ResponseBody
    @PostMapping("/simpleObjectPublisher")
    public ResponseBo simpleObjectPublisher(@RequestBody @Valid User vo) throws JsonProcessingException {
        String msgValue = "simpleBytePublisher";
        long startTime = init(msgValue,objectMapper.writeValueAsString(vo));
        try{
            //注意:真实环境下要用事件异步驱动的方式发起消息
            simpleObjectPublisher.sendObjectMsg(vo);
            endLog(msgValue,startTime);
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
        }
        return ResponseBo.ok();
    }

    /**
     * 广播消费模型-生产者-字节流模式
     * */
    @ApiOperation(value = "广播消费模型-生产者-字节流模式演示",notes = "广播消费模型-生产者-字节流模式演示")
    @ResponseBody
    @PostMapping("/fanoutPublisher")
    public ResponseBo fanoutPublisher(@RequestBody @Valid RabbitMqMsgVo vo) throws JsonProcessingException {
        String msgValue = "fanoutPublisher";
        long startTime = init(msgValue,objectMapper.writeValueAsString(vo));
        try{
            //注意:真实环境下要用事件异步驱动的方式发起消息
            fanoutPublisher.sendMsg(vo.getMsg());
            endLog(msgValue,startTime);
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
        }
        return ResponseBo.ok();
    }

    /**
     * 直连消费模型-生产者-字节流模式 one
     * */
    @ApiOperation(value = "直连消费模型-生产者-字节流模式演示==发送one队列",notes = "广播消费模型-生产者-字节流模式演示==发送one队列")
    @ResponseBody
    @PostMapping("/directPublisherOne")
    public ResponseBo directPublisherOne(@RequestBody @Valid RabbitMqMsgVo vo) throws JsonProcessingException {
        String msgValue = "directPublisherOne";
        long startTime = init(msgValue,objectMapper.writeValueAsString(vo));
        try{
            //注意:真实环境下要用事件异步驱动的方式发起消息
            directPublisher.sendMsgOne(vo.getMsg());
            endLog(msgValue,startTime);
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
        }
        return ResponseBo.ok();
    }

    /**
     * 直连消费模型-生产者-字节流模式 two
     * */
    @ApiOperation(value = "直连消费模型-生产者-字节流模式演示==发送two队列",notes = "广播消费模型-生产者-字节流模式演示==发送two队列")
    @ResponseBody
    @PostMapping("/directPublisherTwo")
    public ResponseBo directPublisherTwo(@RequestBody @Valid RabbitMqMsgVo vo) throws JsonProcessingException {
        String msgValue = "directPublisherTwo";
        long startTime = init(msgValue,objectMapper.writeValueAsString(vo));
        try{
            //注意:真实环境下要用事件异步驱动的方式发起消息
            directPublisher.sendMsgTwo(vo.getMsg());
            endLog(msgValue,startTime);
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
        }
        return ResponseBo.ok();
    }


    /**
     * 订阅消费模型-生产者-字节流模式
     * */
    @ApiOperation(value = "订阅消费模型-生产者-字节流模式演示",notes = "订阅消费模型-生产者-字节流模式演示")
    @ResponseBody
    @PostMapping("/topicPublisher")
    public ResponseBo topicPublisher(@RequestBody @Valid TopicPublisherVo vo) throws JsonProcessingException {
        String msgValue = "topicPublisher";
        long startTime = init(msgValue,objectMapper.writeValueAsString(vo));
        try{
            //注意:真实环境下要用事件异步驱动的方式发起消息
            topicPublisher.sendMsg(vo.getMsg(),vo.getRoutingKey());
            endLog(msgValue,startTime);
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
        }
        return ResponseBo.ok();
    }


    /**
     * 基于manual 机制-人为手动确认消费
     * */
    @ApiOperation(value = "基于manual 机制-人为手动确认消费模式演示",notes = "基于manual 机制-人为手动确认消费模式演示")
    @ResponseBody
    @PostMapping("/manual")
    public ResponseBo manual(@RequestBody @Valid Student vo) throws JsonProcessingException {
        String msgValue = "topicPublisher";
        long startTime = init(msgValue,objectMapper.writeValueAsString(vo));
        try{
            //注意:真实环境下要用事件异步驱动的方式发起消息
            manualPublisher.sendAutoMsg(vo);
            endLog(msgValue,startTime);
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
        }
        return ResponseBo.ok();
    }
}

