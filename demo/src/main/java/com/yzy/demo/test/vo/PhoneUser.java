package com.yzy.demo.test.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PhoneUser  implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5023112818896544461L;
    @ApiModelProperty(" 手机号")
    private String phone;
    @ApiModelProperty(" 充值金额")
    private Double fare;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    //特殊排名使用，手机号相同代表充值记录重复，所以重写equals和hashCode
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        PhoneUser phoneUser = (PhoneUser) o;
        return phone != null ? phone.equals(phoneUser.phone):phoneUser.phone == null;
    }

    public int hashCode(){
        return phone != null?phone.hashCode():0;
    }
}
