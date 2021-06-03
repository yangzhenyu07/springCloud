package com.yzy.demo.startupdata;

import com.yzy.demo.config.redis.RbloomFilterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.PropertyResourceBundle;

/**
 * @author yangzhneyu
 * 项目启动预处理3
 * 作用:springBoot 项目启动预先加载数据
 * 通过@Order来排序，数字越小，越先执行
 * */
@Component
@Order(3)
public class StartUpRunnerThree implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(StartUpRunnerThree.class);
    @Autowired
    private RbloomFilterUtil rbloomFilterUtil;
    @Override
    public void run(String... args) throws Exception {
        log.info("===================项目启动预处理3【布隆过滤器初始化测试】===================");
        Long l= 10000L;
        for (Long i = 0L;i<l;i++){
            rbloomFilterUtil.put(i);
        }
    }
}