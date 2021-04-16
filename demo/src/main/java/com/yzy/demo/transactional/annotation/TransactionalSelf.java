package com.yzy.demo.transactional.annotation;

import java.lang.annotation.*;

/**
 * 编辑自定义事务控制注解
 * @author yangzhenyu
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface TransactionalSelf {
}
