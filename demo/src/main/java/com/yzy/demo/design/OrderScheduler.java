package com.yzy.demo.design;

import com.yzy.demo.design.common.Template;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 定时器[分布式锁]
 * */
@Configuration      //标记为配置类
@EnableAsync        //开启多线程
@EnableScheduling   //开启定时任务，可以放到启动类中
public class OrderScheduler {
    @Value("${config.scheduled.flag:false}")
    private Boolean isRunSchedule;
    @Autowired
    List<Template> services;
    private static final String KEY = "mylock";
    @Autowired
    private RedissonClient redissonClient;
    private static Logger log = LoggerFactory.getLogger(OrderScheduler.class);

    /**
     * 任务入口
     */
    //@Async
    @Scheduled(cron = "${config.scheduled.work_one:0/5 * * * * ?}")
    public void excute() {
        if(isRunSchedule) {
            log.info("lock1  正在获取锁。。。。");
            RLock lock = redissonClient.getLock(KEY);
            log.info(Thread.currentThread().getName() + ":" + Thread.currentThread().getId() + " lock1 已经获取到锁");
            try{
                lock.lock();
                    log.info("----------------定时器开始------------------");
                    for (Template service : services) {
                        try {
                            service.start();
                        } catch (Exception e) {
                            log.error("堆栈信息:", e);
                        }
                    }
                    log.info("----------------定时器结束------------------");
            }finally {
                lock.unlock();
                log.info(Thread.currentThread().getName() + ":" + Thread.currentThread().getId() + " lock1 已解锁");
            }
        }

    }
}
