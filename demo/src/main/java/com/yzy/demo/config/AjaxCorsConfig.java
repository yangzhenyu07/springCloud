package com.yzy.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 *  处理ajax跨域请求
 * @author yangzhenyu
 * */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnExpression("${config.cors:false} == true")
public class AjaxCorsConfig {

    private static Logger log = LoggerFactory.getLogger(AjaxCorsConfig.class);

    /**
     * 设置允许访问的域名
     *
     * @return CorsConfiguration对象
     */
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许任何域名访问
        corsConfiguration.addAllowedOrigin("*");
        //允许任何header访问
        corsConfiguration.addAllowedHeader("*");
        //允许任何方法访问
        corsConfiguration.addAllowedMethod("*");
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        corsConfiguration.setMaxAge(18000L);
        //允许访问
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    /**
     * 实例化拦截器
     *
     * @return CorsFilter对象
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
        log.info("CORS跨域拦截配置：允许所有请求访问！");
        return bean;
    }
}
