package com.yzy.demo.eventdrive;

import org.springframework.context.ApplicationEvent;

import java.io.Serializable;
/**
 * 用户登录成功后的事件实体
 * @author yangzhenyu
 * */
public class LoginEvent extends ApplicationEvent implements Serializable {
    private String name; //用户名
    private String time; //用户登录时间
    private String ip; //用户登录ip



    public LoginEvent(Object source) {
        super(source);
    }
    /**
     * 继承ApplicationEvent 类时重写的方法
     * */
    public LoginEvent(Object source,String name, String time, String ip) {
        super(source);
        this.name = name;
        this.time = time;
        this.ip = ip;
    }

    //================================================================================
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
