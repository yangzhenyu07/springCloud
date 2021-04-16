package com.yzy.demo.config;

import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

/**
 *  tomcat 配置类
 * @author yangzhenyu
 * */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnExpression("${config.tomcat.request:false} == true")
public class TomcatConfig {

    private static Logger log = LoggerFactory.getLogger(TomcatConfig.class);

    /**
     * 设置那些方法不可以请求,不设置默认  PUT,DELETE,CONNECT,OPTIONS,PATCH,PROPFIND,PROPPATCH,MKCOL,COPY,MOVE,LOCK,UNLOCK,TRACE,HEAD
     **/
    @Value("${config.tomcat.request-method:PUT,DELETE,CONNECT,OPTIONS,PATCH,PROPFIND,PROPPATCH,MKCOL,COPY,MOVE,LOCK,UNLOCK,TRACE,HEAD}")
    private String type;

    /**
     * 限制不安全的请求方法
     */
    @Bean
    public ConfigurableServletWebServerFactory servletContainer() {
        String[] types = type.split(",");
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addContextCustomizers(context -> {
            SecurityConstraint securityConstraint = new SecurityConstraint();
            securityConstraint.setUserConstraint("CONFIDENTIAL");
            SecurityCollection collection = new SecurityCollection();
            collection.addPattern("/*");
            for (String type : types) {
                // 限制不安全的方法
                collection.addMethod(type.toUpperCase(Locale.CHINESE));
            }
            securityConstraint.addCollection(collection);
            context.setUseHttpOnly(true);
            context.addConstraint(securityConstraint);
        });
        log.info("tomcat拦截不安全的方法：{}", type);
        return factory;
    }
}
