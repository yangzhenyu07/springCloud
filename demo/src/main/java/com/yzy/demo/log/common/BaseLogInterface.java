package com.yzy.demo.log.common;

public interface BaseLogInterface {
    /**
     * 返回时间日志
     * */
    void endLog(String msg,long startTime);
    /**
     * 返回时间错误日志
     * */
    void endLogError(String msg,long startTime,Throwable e);
    /**
     * 返回开始时间
     * */
    long startLog(String msgKey,String msgValue);

    long init(String name,String param);
    void error(String mag);
    void info(String msg);
}
