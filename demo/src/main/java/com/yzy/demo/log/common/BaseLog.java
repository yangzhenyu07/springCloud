package com.yzy.demo.log.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangzhenyu
 * 日志模板
 * */
public abstract  class BaseLog implements BaseLogInterface{
    private static Logger log = LoggerFactory.getLogger(BaseLog.class);
    private ReentrantLock lock = new ReentrantLock();
    private String clazz;

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    /**
     * 打印错误日志
     * */
    public void error(String msg){
        try{
            lock.lock();
            log.error(msg);
        }finally {
            lock.unlock();
        }
    }
    /**
     * 打印普通日志
     * */
    public void info(String msg){
        try{
            lock.lock();
              log.info(msg);
        }finally {
            lock.unlock();
        }
    }
    /**
     * 返回时间日志
     * */
    public final void endLog(String msg,long startTime){
        try{
            lock.lock();
            long endTime = System.currentTimeMillis();
            log.info("<======执行源:【{}】==> {}执行结束，耗时:{}秒 =======>",clazz,msg,((endTime-startTime)/1000));
        }finally {
            lock.unlock();
        }

    }
    /**
     * 返回时间错误日志
     * */
    public final void endLogError(String msg,long startTime,Throwable e){
        try{
            lock.lock();
            long endTime = System.currentTimeMillis();
            log.error(String.format("<======执行源:【%s】==> %s执行结束，耗时:%s秒 =======>",clazz,msg,((endTime-startTime)/1000)),e);
        }finally {
            lock.unlock();
        }

    }
    /**
     * 返回开始时间
     * */
    public final long startLog(String msgKey,String param){
        try{
            lock.lock();
            log.info("<======执行源:【{}】==>方法[{}}]开始执行  =======>",clazz,msgKey);
            log.info("<======参数：【{}】  =======>",param);
            return System.currentTimeMillis();
        }finally {
            lock.unlock();
        }
    }
}
