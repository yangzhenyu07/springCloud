package com.yzy.demo.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一返回数据拦截
 * */
@ControllerAdvice
@ConditionalOnWebApplication
public class DataResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static Logger log = LoggerFactory.getLogger(DataResponseBodyAdvice.class);

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter parameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> converter, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("请求url:{},返回数据:{}",request.getURI().getPath()  ,object);
        response.getHeaders().add("YZY", "YZY");
        return object;
    }

    @Override
    public boolean supports(MethodParameter parameter, Class<? extends HttpMessageConverter<?>> converter) {
        return true;
    }
    public DataResponseBodyAdvice() {
        log.info("===================统一返回数据拦截配置===================");
    }

}

