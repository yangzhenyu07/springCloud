package com.yzy.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author yangzhneyu
 * 定时器配置
 * */
@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {

    private static Logger log = LoggerFactory.getLogger(SchedulingConfig.class);

    public SchedulingConfig() {
        log.info("===================集成定时器配置===================");
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor());
    }
    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor(){
        //指定线程池大小
        return Executors.newScheduledThreadPool(10);
    }

}
