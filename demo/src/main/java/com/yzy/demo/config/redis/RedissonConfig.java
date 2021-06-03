package com.yzy.demo.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Redisson 配置
 * @author yangzhenyu
 * */
@Component
@ConfigurationProperties(prefix = "redisson.config", ignoreUnknownFields = true)
public class RedissonConfig {

    private static Logger log = LoggerFactory.getLogger(RedissonConfig.class);
    public RedissonConfig() {
        log.info("=================== Redisson 配置===================");
    }

    @Value("redis://127.0.0.1:6379")
    private String host;

    /** redis索引 **/
    @Value("0")
    private Integer database;

    /** 等待节点回复命令的时间。该时间从命令发送成功时开始计时 **/
    @Value("3000")
    private Integer timeout = 3000;

    /**
     * 从节点发布到订阅连接的最小空闲连接数
     * */
    @Value("8")
    private Integer minIdle;
    /**
     * 连接池配置数量
     * */
    @Value("64")
    private Integer size;
    @Bean
    public RedissonClient config(){
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer();
        //单一节点模式
        serverConfig.setAddress(host.startsWith("redis://") ? host : "redis://" + host).setKeepAlive(true);
        serverConfig.setDatabase(database);
        serverConfig.setTimeout(timeout);
        serverConfig.setConnectionPoolSize(size);
        serverConfig.setConnectionMinimumIdleSize(minIdle);
        return Redisson.create(config);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
