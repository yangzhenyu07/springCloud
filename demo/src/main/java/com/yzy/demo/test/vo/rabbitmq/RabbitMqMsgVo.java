package com.yzy.demo.test.vo.rabbitmq;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * author : yangzhenyu
 * **/
public class RabbitMqMsgVo implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5023112818896544461L;
    @NotNull(message = "msg不能null")
    @ApiModelProperty("生产者模拟消息")
    private String msg ;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
