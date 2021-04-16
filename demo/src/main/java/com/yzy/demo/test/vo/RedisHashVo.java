package com.yzy.demo.test.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Redis 哈希 案例测试vo
 * */
public class RedisHashVo {
    @ApiModelProperty(" 学生 检索ID")
    private String sIndex;
    @ApiModelProperty(" 学生 存储缓存内容")
    private List<Student> sList;
    @ApiModelProperty(" 老师 检索ID")
    private String tIndex;
    @ApiModelProperty(" 老师 存储缓存内容")
    private List<Teacher> tList;

    public String getsIndex() {
        return sIndex;
    }

    public void setsIndex(String sIndex) {
        this.sIndex = sIndex;
    }

    public List<Student> getsList() {
        return sList;
    }

    public void setsList(List<Student> sList) {
        this.sList = sList;
    }

    public String gettIndex() {
        return tIndex;
    }

    public void settIndex(String tIndex) {
        this.tIndex = tIndex;
    }

    public List<Teacher> gettList() {
        return tList;
    }

    public void settList(List<Teacher> tList) {
        this.tList = tList;
    }
}
