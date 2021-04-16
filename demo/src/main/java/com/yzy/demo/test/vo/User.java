package com.yzy.demo.test.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * 用户
 *
 * */
public class User implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -5023112818896544461L;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty(" userId")
    private String userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
