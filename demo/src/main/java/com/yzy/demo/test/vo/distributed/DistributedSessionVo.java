package com.yzy.demo.test.vo.distributed;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
/**
 * 分布式会话生成Vo
 * */
public class DistributedSessionVo implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -5023112818896544461L;
    private String id;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商品份数")
    private String number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
