package com.yzy.demo.aop.aspect;

import com.yzy.demo.aop.annotation.AopTest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * AOP切面练习
 * @author yangzhenyu
 * */
@Aspect
@Component
public class AopTestAspect {
    private static Logger log = LoggerFactory.getLogger(AopTestAspect.class);
    @Pointcut("@annotation(com.yzy.demo.aop.annotation.AopTest)")
    public void pointcut(){};

    /**
     * 注解式拦截
     * */
    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        AopTest aopTest = method.getAnnotation(AopTest.class);
        log.info("=============【@Before】=【{}】,当前执行的方法名：【{}】================",aopTest.name(),method.getName());
    }
    /**
     * 注解式拦截
     * */
    @AfterReturning(returning = "obc",pointcut = "pointcut()")
    public void afterReturning(JoinPoint joinPoint,Object obc){
        log.info("=============【@AfterReturning】=【方法规则拦截】,返回数据：【{}】================",obc.toString());
    }
    /**
     * 方法规则式拦截
     * */
    @After("execution(* com.yzy.demo.test.service.TestAopTransactionalService.*(..))")
    public void after(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("=============【@After】=【方法规则拦截】,当前执行的方法名：【{}】================",method.getName());

    }
}
