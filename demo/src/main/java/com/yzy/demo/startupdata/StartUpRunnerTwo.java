package com.yzy.demo.startupdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.PropertyResourceBundle;

/**
 * @author yangzhneyu
 * 项目启动预处理2
 * 作用:springBoot 项目启动预先加载数据
 * 通过@Order来排序，数字越小，越先执行
 * */
@Component
@Order(2)
public class StartUpRunnerTwo  implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(StartUpRunnerTwo.class);
    //参数变量配置
    private static final PropertyResourceBundle params = (PropertyResourceBundle) PropertyResourceBundle.getBundle("params");

    @Override
    public void run(String... args) throws Exception {
        log.info("===================项目启动预处理2【抓取参数配置文件测试】===================");
        Enumeration paramKeys= params.getKeys();
        while(paramKeys.hasMoreElements()){
            String key = paramKeys.nextElement().toString();
            log.info("参数【{}】：{}",key,params.getString(key));
        }
    }
}