package com.yzy.demo.config.redis;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 布隆过滤器
 * 使用场景:主要是针对不存在的,进行查询短路操作,避免无效的查询
 * author : yangzhenyu
 * **/
@Component
@ConfigurationProperties(prefix = "redisson.rbloomfilter", ignoreUnknownFields = true)
public class RbloomFilterUtil {
    private static Logger log = LoggerFactory.getLogger(RbloomFilterUtil.class);
    public RbloomFilterUtil() {
        log.info("=================== 布隆过滤器初始化===================");
    }
    @Autowired
    private RedissonClient redisson;
    private RBloomFilter<Long> bloomFilter;
    @Value("test")
    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    //==================================================================================================================
    //==================================================================================================================

    /**
     * 执行顺序:构造方法 > @Autowired > @PostConstruct
     * 在项目中主要是在Servlet初始化之前加载一些缓存数据等
     * 1、只有一个非静态方法能使用此注解
     * 2、被注解的方法不得有任何参数
     * 3、被注解的方法返回值必须为void
     * 4、被注解方法不得抛出已检查异常
     * 5、此方法只会被执行一次
     * */
    @PostConstruct
    private void initBloomFilter(){
        this.bloomFilter = redisson.getBloomFilter(key);
        //初始化布隆过滤器：预计元素为10000L,误差率为3%
        this.bloomFilter.tryInit(10000L,0.03);
        //测试设置过期时间
        //this.bloomFilter.expire(180L, TimeUnit.SECONDS);
    }

    public boolean isContains(Long id){
        return this.bloomFilter.contains(id);
    }

    public void put(Long id){
        this.bloomFilter.add(id);
    }


}
