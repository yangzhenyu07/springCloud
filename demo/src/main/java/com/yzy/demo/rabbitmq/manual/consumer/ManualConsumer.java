package com.yzy.demo.rabbitmq.manual.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.yzy.demo.test.vo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 认为手动确认消费-消费者-字节流模式
 * @author yangzhenyu
 * */
@Component("manualConsumer")
public class ManualConsumer implements ChannelAwareMessageListener {
    private static Logger log = LoggerFactory.getLogger(ManualConsumer.class);

    //序列化和返序列化
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //获取消息属性
        MessageProperties messageProperties = message.getMessageProperties();
        //获取消息分发时的全局唯一标识
        long tag = messageProperties.getDeliveryTag();
        try{
            //获得消息体
            byte [] msg = message.getBody();
            //解析消息体
            Student student = objectMapper.readValue(msg,Student.class);
            log.info("基于manual机制-确认消息模式-人为手动确定消费-监听到消息:【{}】",objectMapper.writeValueAsString(student));
            //执行完逻辑后手动确认，第一个参数代表消息的分发标识(全局唯一)，第二个参数代表是否允许批量确认消费
            channel.basicAck(tag,true);
        }catch (Exception e){
            log.error("确认消息模式-人为手动确定消费-发生异常:",e.fillInStackTrace());
            /**
             * 如果在处理消息的过程中发生异常，则需要人为手动确认消费掉该消息
             * 否则该消息将一直停留在队列中，从而导致重复消费
             * */
            channel.basicReject(tag,false);
        }
    }
}
