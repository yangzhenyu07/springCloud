package com.yzy.demo.session.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzy.demo.test.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 分布式会话 获取
 * */
@Aspect
@Component
public class MySessionAspect {

    //分布式 sessionId 的超时时间
    @Value("${config.session.sessionTimeOut:5}")
    private long sessionTimeOut;
    //缓存中的命名前缀【防缓存雪崩设计】
    @Value("${config.session.head:yzy}")
    private String keyHead;
    //存放cookie的sessionId的key值
    @Value("${config.session.sessionKey:SESSION_KEY}")
    private String sessionKey;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    private static Logger log = LoggerFactory.getLogger(MySessionAspect.class);
    @Pointcut("@annotation(com.yzy.demo.session.annotation.MySession)")
    public void pointcut(){};

    /**
     * 获取session数据所保存的入参对象
     * */
    public Map<String,String> getSessionParam(ProceedingJoinPoint joinPoint){
        Map<String,String> map = null;
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        for (int i=0;i<parameterNames.length;i++){
            if ("session".equals(parameterNames[i])){
                map = (Map) args[i];
                break;
            }
        }
        if (null == map){
            map = new HashMap<>();
        }
        return map;
    }
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws JsonProcessingException {
        //类名
        String className = joinPoint.getTarget().getClass().getName();
        //方法名
        String methodName = joinPoint.getSignature().getName();
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Object result = null;
         String sessionId = getCookie(request,sessionKey);
        Map<String,String> map = getSessionParam(joinPoint);
        try {
            //返回值
            Object[] args = joinPoint.getArgs();
            //session逻辑
            if (null == sessionId){
                return joinPoint.proceed(args);
            }
            /**
             * 设计思路:
             * 当缓存中无【sessionId】，说明已过期
             *
             *
             * */
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

            final String value = valueOperations.get(sessionId);
            if (StringUtils.isEmpty(value)){
                return joinPoint.proceed(args);
            }
            User user = objectMapper.readValue(value, User.class);
            map.put("name",user.getName());
            map.put("userId",user.getUserId());
            result = joinPoint.proceed(args);
        }catch (Exception e){
            log.error("获取session出现错误",e);
            log.error(String.format("【%s】类执行【%s】方法，执行失败!!!",className,methodName),e);
            return result;
        } catch (Throwable throwable) {
            log.error(String.format("【%s】类执行【%s】方法，执行失败!!!",className,methodName),throwable);
            return null;
        }
        return result;
    }

    /**
     * 从cookie中获取sessionId
     * */
    public String getCookie(HttpServletRequest request, String key){
        if (request == null){
            return null;
        }
        if (null == key||key.isEmpty()){
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (null == cookies){
            return null;
        }
        for (Cookie cookie:cookies){
            if (key.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
