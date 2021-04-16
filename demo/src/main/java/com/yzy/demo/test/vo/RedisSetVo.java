package com.yzy.demo.test.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/***
 * redis 集合
 * @author yangzhenyu
 */
public class RedisSetVo {
    @NotNull(message = "redis key 不能为空")
    @ApiModelProperty(" Redis key值 ")
    private String key;
    @ApiModelProperty(" Redis 列表 ")
    private List<String> list;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
