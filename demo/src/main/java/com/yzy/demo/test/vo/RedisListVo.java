package com.yzy.demo.test.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/***
 * redis 列表
 * @author yangzhenyu
 */
public class RedisListVo {
    @ApiModelProperty(" Redis 列表 ")
    private List<RedisVauleModel> list;
    @NotNull(message = "redis key 不能为空")
    @ApiModelProperty(" Redis key值 ")
    private String key;
    public List<RedisVauleModel> getList() {
        return list;
    }

    public void setList(List<RedisVauleModel> list) {
        this.list = list;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
