package com.yzy.demo.test.vo;

import java.io.Serializable;
/**
 * test vo
 * @author yangzhenyu
 * */
public class TestQuert implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5023112818896544461L;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
