package com.yzy.demo.test.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * author : yangzhenyu
 * **/
public class RedisVauleModel implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5023112818896544461L;
    @ApiModelProperty(" id值")
    private String id;
    @ApiModelProperty("vaule 值")
    private String vaulebsc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVaulebsc() {
        return vaulebsc;
    }

    public void setVaulebsc(String vaulebsc) {
        this.vaulebsc = vaulebsc;
    }
}

