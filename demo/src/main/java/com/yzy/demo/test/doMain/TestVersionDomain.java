package com.yzy.demo.test.doMain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.JdbcType;
/**
 * test  doMain
 * @author yangzhenyu
 * */
@TableName(value = "TEST_VERSION")
public class TestVersionDomain {
    /**
     * id
     * */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * VERSION
     * */
    @TableField(value = "VERSION",jdbcType = JdbcType.VARCHAR)
    private String version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
