package com.yzy.demo.config;

import com.yzy.demo.startupdata.StartUpRunnerOne;
import com.yzy.demo.utils.HttpClientHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;
/**
 * 优雅关机
 * 注意:不建议应用自定义hook，因为java的hook执行是无序的，可能会产生无法预知的问题，
 * 建议应用将自身停机逻辑委托给spring管理
 * */
@Component
@ConfigurationProperties(prefix = "eureka.down", ignoreUnknownFields = true)
public class ShutDownListener implements SmartApplicationListener {
    private final static String DOWN = null;
    @Value("http://127.0.0.1:19001/actuator/service-registry?status=DOWN")
    private String url;
    private final static String TYPE = "application/vnd.spring-boot.actuator.v2+json;";
    private static Logger log = LoggerFactory.getLogger(ShutDownListener.class);

    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        //将当前线程的堆栈跟踪打印到标准错误流。它仅用于调试。
        //Thread.currentThread().dumpStack();
        //改变注册信息状态 down
        String s = HttpClientHelper.httpPostJson(url, DOWN, TYPE,"UTF-8", null);
        log.info("【ShutDownListener】==========================================【{}】",s);
    }
    @Override
    public int getOrder() {
        //order ，数字越小，优先级越高，越先执行
        return 0;
    }
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        //监听spring context close 事件
        return aClass == ContextClosedEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        //设置为true
        return true;
    }


}
