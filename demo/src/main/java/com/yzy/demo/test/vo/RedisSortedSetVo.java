package com.yzy.demo.test.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/***
 * redis 有序集合演示VO
 * @author yangzhenyu
 */
public class RedisSortedSetVo {
    @ApiModelProperty(" 无序的手机用户充值列表")
    private List<PhoneUser> list;
    @NotNull(message = "redis key 不能为空")
    @ApiModelProperty(" redis key ")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<PhoneUser> getList() {
        return list;
    }

    public void setList(List<PhoneUser> list) {
        this.list = list;
    }
}
