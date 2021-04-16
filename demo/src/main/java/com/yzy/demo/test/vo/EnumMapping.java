package com.yzy.demo.test.vo;

import java.io.Serializable;

public class EnumMapping implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5023112818896544461L;
    private String  id;
    private String enumItemCode;
    private String enumCode;
    private String systemId;
    private String targetValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnumItemCode() {
        return enumItemCode;
    }

    public void setEnumItemCode(String enumItemCode) {
        this.enumItemCode = enumItemCode;
    }

    public String getEnumCode() {
        return enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }
}
