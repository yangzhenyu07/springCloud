package com.yzy.demo.test.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * author : yangzhenyu
 * **/
public class RedisModel {
    @NotNull(message = "redis key值不能为null")
    @ApiModelProperty("redis key值")
    private String key ;
    @NotNull(message = "redis vaule值不能为空")
    @ApiModelProperty("redis vaule值")
    private RedisVauleModel vaule;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public RedisVauleModel getVaule() {
        return vaule;
    }

    public void setVaule(RedisVauleModel vaule) {
        this.vaule = vaule;
    }
}
