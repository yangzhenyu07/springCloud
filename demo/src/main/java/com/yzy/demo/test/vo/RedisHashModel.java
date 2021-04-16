package com.yzy.demo.test.vo;


import io.swagger.annotations.ApiModelProperty;
/**
 * author : yangzhenyu
 * **/
public class RedisHashModel {

    @ApiModelProperty("redis key值")
    private String key ;
    @ApiModelProperty("redis vaule值")
    private String value;
    @ApiModelProperty("redis HASH_name值")
    private String name;

    @ApiModelProperty("redis 删除Hash值")
    private String deleteId;

    public RedisHashModel(String key, String value, String name) {
        this.key = key;
        this.value = value;
        this.name = name;
    }

    public String getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
