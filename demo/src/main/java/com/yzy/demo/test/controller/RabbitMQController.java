package com.yzy.demo.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzy.demo.log.common.BaseLog;
import com.yzy.demo.rabbitmq.fanout.publisher.FanoutPublisher;
import com.yzy.demo.rabbitmq.test.publisher.SimpleBytePublisher;
import com.yzy.demo.rabbitmq.test.publisher.SimpleObjectPublisher;
import com.yzy.demo.test.vo.User;
import com.yzy.demo.test.vo.rabbitmq.RabbitMqMsgVo;
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
    //广播消费模型-生产者
    @Autowired
    private FanoutPublisher fanoutPublisher;
    @Autowired
    private ObjectMapper objectMapper;
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
}

