package com.yzy.demo.test.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * author : yangzhenyu
 * **/
public class FastDfsVo {
    @NotNull(message = "上传文件路径不能为null")
    @ApiModelProperty("上传文件路径")
    private String key ;
    @NotBlank(message = "下载文件类型不能为空")
    @ApiModelProperty("下载文件类型")
    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
