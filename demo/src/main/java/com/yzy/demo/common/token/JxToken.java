package com.yzy.demo.common.token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/**
 * token 解析
 * @author yangzhenyu
 * */
public abstract  class JxToken {

    final public Map<String,Object> getInfo(HttpServletRequest request) {
        final String authHeader = request.getHeader("authorization");
        final String token = authHeader.substring(7);
        final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
        Map<String,Object> map = new HashMap<>();
        map.put("name",(String) claims.get("name"));
        map.put("age",(String) claims.get("age"));
        return map;
    }
}
