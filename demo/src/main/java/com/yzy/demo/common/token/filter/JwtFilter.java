package com.yzy.demo.common.token.filter;
import com.alibaba.fastjson.JSON;
import com.yzy.demo.utils.ResponseBo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * @author yangzhneyu
 * */
public class JwtFilter extends GenericFilterBean {
    private static Logger log = LoggerFactory.getLogger(JwtFilter.class);
    public JwtFilter() {
        log.info("===================token拦截配置===================");
    }

    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        final String authHeader = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        }
        else {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                PrintWriter out = response.getWriter();
                log.error("token错误：token={}",request.getRequestURL());
                out.print(JSON.toJSON(ResponseBo.error("token错误")));
                out.flush();
                return;
            }

            final String token = authHeader.substring(7);
            try {
                final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
                long exp=claims.getExpiration().getTime();
                if(System.currentTimeMillis()>=exp){
                    log.error("token过期：token={}",request.getRequestURL());
                    PrintWriter out = response.getWriter();
                    out.print(JSON.toJSON(ResponseBo.error("token过期")));
                    out.flush();
                    return;
                }else {
                    request.setAttribute("claims", claims);
                }
            } catch (final Exception e) {
                PrintWriter out = response.getWriter();
                out.print(JSON.toJSON(ResponseBo.error("token过期")));
                out.flush();
                return;
            }

            chain.doFilter(req, res);
        }
    }
}