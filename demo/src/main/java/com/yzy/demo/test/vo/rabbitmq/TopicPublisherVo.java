package com.yzy.demo.test.vo.rabbitmq;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TopicPublisherVo implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5023112818896544461L;
    @NotNull(message = "msg不能null")
    @ApiModelProperty("生产者模拟消息")
    private String msg;
    @NotNull(message = "msg不能null")
    @ApiModelProperty("路由")
    private String routingKey;

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
