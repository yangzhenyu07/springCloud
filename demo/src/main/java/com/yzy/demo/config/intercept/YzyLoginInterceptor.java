package com.yzy.demo.config.intercept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * */
@Component
public class YzyLoginInterceptor implements LoginInterceptor {

    private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    public YzyLoginInterceptor() {
        log.info("===================拦截器配置===================");
    }
    /**
     *在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理
     * */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  throws Exception {
        log.info("request请求地址path[{}] , uri[{}]", request.getServletPath(),request.getRequestURI());
        return true;
    }

    /**
     *在业务处理器处理请求执行完成后，生成视图之前执行。后处理
     * */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理
     * */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)    throws Exception {

    }

}
