package com.yzy.demo.config.processor;

import org.springframework.core.env.ConfigurableEnvironment;
/**
 * 单例  自定义环境对象
 * @yangzhenyu
 * */
public class Singleton {

    private static volatile   Singleton instance;

    private ConfigurableEnvironment environment;

    public ConfigurableEnvironment getEnvironment() {
        return environment;
    }

    private  Singleton(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    public static   Singleton getInstance(ConfigurableEnvironment environment,String key) {
        if("YES".equals(key)){
            instance = new Singleton(environment);
        }
        if(instance == null) {
            synchronized(Singleton.class){
                instance = new Singleton(environment);
            }
        }
        return instance;
    }
}
