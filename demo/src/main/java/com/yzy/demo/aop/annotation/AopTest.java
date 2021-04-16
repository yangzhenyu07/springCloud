package com.yzy.demo.aop.annotation;

import java.lang.annotation.*;
/**
 * AOP切面练习
 * @author yangzhenyu
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AopTest {
    String name() default "";
}
