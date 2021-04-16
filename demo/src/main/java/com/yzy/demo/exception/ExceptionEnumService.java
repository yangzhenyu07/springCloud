package com.yzy.demo.exception;
/**
 * 所有异常信息接口
 * @author yangzhenyu
 **/
public interface ExceptionEnumService {
    /**
     * 异常编码
     *
     * @return 异常编码
     */
    String getCode();

    /**
     * 异常信息
     *
     * @return 异常信息
     */
    String getDesc();

}
