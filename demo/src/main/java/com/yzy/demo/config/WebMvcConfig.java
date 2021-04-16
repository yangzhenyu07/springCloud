package com.yzy.demo.config;

import com.yzy.demo.config.intercept.LoginInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * spring mvc 配置类
 * @author yangzhenyu
 **/
@ConditionalOnWebApplication
@Configuration("yzy-mvc-config")
public class WebMvcConfig  implements WebMvcConfigurer {

    /**
     * 权限接口
     */
    @Resource
    private LoginInterceptor loginInterceptor;
    /**
     * 解决编码问题
     *
     * @param converters 消息解析器集合
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 添加一个默认的字符串编码解析器（utf-8编码）
        converters.add(0, responseBodyStringConverter());
    }

    @Bean
    public HttpMessageConverter<String> responseBodyStringConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

    /**
     * 拦截所有的请求
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");

    }
}
