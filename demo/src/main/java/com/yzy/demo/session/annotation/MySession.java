package com.yzy.demo.session.annotation;

import java.lang.annotation.*;
/**
 * 分布式会话 获取
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface MySession {
}
