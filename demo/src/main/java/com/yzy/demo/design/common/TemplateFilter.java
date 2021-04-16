package com.yzy.demo.design.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract  class TemplateFilter implements Template{
    private static Logger log = LoggerFactory.getLogger(TemplateFilter.class);
    private String msg = "";

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Template three(){
        log.info("【{}】第三步操作启动",msg);
        return this;
    }
    public Template four(){
        log.info("【{}】第四步操作启动",msg);
        return this;
    }
    public void start(){
        log.info("任务启动");
        this.one().two().three().four().end();
    }

    public void end(){
        log.info("【{}】任务完成",msg);
        msg = "";
    }
}
