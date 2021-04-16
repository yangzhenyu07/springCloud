package com.yzy.demo.test.doMain;


import org.apache.ibatis.type.JdbcType;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * test  doMain
 * @author yangzhenyu
 * */
@TableName(value = "TEST")
public class TestDomain {
    /**
     * id
     * */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * age
     * */
    @TableField(value = "AGE",jdbcType = JdbcType.VARCHAR)
    private String age;
    /**
     * name
     * */
    @TableField(value = "NAME",jdbcType = JdbcType.VARCHAR)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
