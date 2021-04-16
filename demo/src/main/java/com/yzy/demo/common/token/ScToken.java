package com.yzy.demo.common.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 生成token
 * @author yangzhenyu
 * */
public class ScToken {
    /**过期时间*/
    public String time = "3600";
    /**赋值*/
    @Value("${config.token.time:1000}")
    public void setTime(String time) {
        this.time = time;
    }
    public String initToken(Map<String,String> map){
        long exp = System.currentTimeMillis() + (1000 * Integer.parseInt(time));
        Claims claims = new DefaultClaims();
        claims.put("age", map.get("age"));
        claims.put("name", map.get("name"));
        return Jwts.builder().setClaims(claims).setExpiration(new Date(exp)).signWith(SignatureAlgorithm.HS256, "secretkey").compact();
    }

}
