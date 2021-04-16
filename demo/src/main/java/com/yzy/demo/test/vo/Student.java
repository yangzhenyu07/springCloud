package com.yzy.demo.test.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 学生
 * @author yangzhenyu
 * */
public class Student implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -5023112818896544461L;
    @NotNull(message = "学生 id值不能为null")
    @ApiModelProperty(" 学生 id值")
    private String sId;
    @ApiModelProperty(" 学生 名称")
    private String sName;
    @ApiModelProperty(" 学生 班级")
    private String className;

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
