package com.yzy.demo.common.token;
import com.yzy.demo.common.token.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
/**
 * @author yangzhneyu
 * */
@Configuration
public class JwtCfg {
    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/api/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*,/swagger-resources/**,/webjars/**,/v2/**,/swagger-ui.html/**,/user/restpw");
        initParameters.put("isIncludeRichText", "true");
        registrationBean.setInitParameters(initParameters);
        return registrationBean;
    }
}
