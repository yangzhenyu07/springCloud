package com.yzy.demo.test.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 教师
 * @author yangzhenyu
 * */
public class Teacher implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -5023112818896544461L;
    @NotNull(message = "老师 id值不能为null")
    @ApiModelProperty(" 老师 id值")
    private String tId;
    @ApiModelProperty(" 老师 姓名")
    private String tName;
    @ApiModelProperty(" 老师 年龄")
    private int tAge;

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public int gettAge() {
        return tAge;
    }

    public void settAge(int tAge) {
        this.tAge = tAge;
    }
}
