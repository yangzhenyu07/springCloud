package com.yzy.demo.startupdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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


    @Override
    public void run(String... args) throws Exception {
        log.info("===================项目启动预处理2===================");
    }
}