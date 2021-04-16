package com.yzy.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * @author yangzhenyu
 * 异步线程池配置
 * */
@Component
@ConfigurationProperties(prefix = "spring.sgrain.async-thread-pool", ignoreUnknownFields = true)
public class AsyncThreadPoolProperties {
    /**
     * 是否启动异步线程池，默认 false
     */
    @Value("false")
    private boolean enable;
    /**
     * 核心线程数,默认：Java虚拟机可用线程数
     */
    @Value("8")
    private Integer corePoolSize;
    /**
     * 线程池最大线程数,默认：40000
     */
    @Value("40000")
    private Integer maxPoolSize;
    /**
     * 线程队列最大线程数,默认：80000
     */
    @Value("80000")
    private Integer queueCapacity;
    /**
     * 自定义线程名前缀，默认：Async-ThreadPool-
     */
    @Value("Async-ThreadPool-")
    private String threadNamePrefix;
    /**
     * 线程池中线程最大空闲时间，默认：60，单位：秒
     */
    @Value("60")
    private Integer keepAliveSeconds = 60;
    /**
     * 核心线程是否允许超时，默认false
     */
    @Value("false")
    private boolean allowCoreThreadTimeOut;
    /**
     * IOC容器关闭时是否阻塞等待剩余的任务执行完成，默认:false（必须设置setAwaitTerminationSeconds）
     */
    @Value("false")
    private boolean waitForTasksToCompleteOnShutdown;
    /**
     * 阻塞IOC容器关闭的时间，默认：10秒（必须设置setWaitForTasksToCompleteOnShutdown）
     */
    @Value("10")
    private int awaitTerminationSeconds ;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public Integer getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(Integer keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public boolean isAllowCoreThreadTimeOut() {
        return allowCoreThreadTimeOut;
    }

    public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
    }

    public boolean isWaitForTasksToCompleteOnShutdown() {
        return waitForTasksToCompleteOnShutdown;
    }

    public void setWaitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
        this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
    }

    public int getAwaitTerminationSeconds() {
        return awaitTerminationSeconds;
    }

    public void setAwaitTerminationSeconds(int awaitTerminationSeconds) {
        this.awaitTerminationSeconds = awaitTerminationSeconds;
    }
}
