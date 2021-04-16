package com.yzy.demo.test.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 解析json文件
 * */
public class JxFileVo implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5023112818896544461L;

    private String extractId;
    private String transId;
    private List<EnumMapping> list;

    public String getExtractId() {
        return extractId;
    }

    public void setExtractId(String extractId) {
        this.extractId = extractId;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public List<EnumMapping> getList() {
        return list;
    }

    public void setList(List<EnumMapping> list) {
        this.list = list;
    }
}
