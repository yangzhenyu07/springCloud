package com.yzy.demo.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnExpression("${config.log.falg:false} == true")
public class LogConfig {
    /** 需要日志拦截的包的集合 **/
    public static String [] filter;


    /********** 赋值 **********/
    @Value("${config.log.filter:com.yzy,com.baomidou.mybatisplus}")
    private void setFilter(String [] filter) {
        LogConfig.filter = filter;
    }

}
