package com.yzy.demo.test.service;

import com.yzy.demo.utils.ResponseBo;


/**
 * Aop 练习 + 自定义编辑事务[异常]练习
 * @author yangzhenyu
 * */
public interface TestAopTransactionalService {
    ResponseBo transactional(String value);

}
