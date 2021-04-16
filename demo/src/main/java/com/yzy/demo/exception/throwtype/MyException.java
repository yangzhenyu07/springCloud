package com.yzy.demo.exception.throwtype;

import com.yzy.demo.exception.ExceptionEnumService;
import org.apache.commons.lang.StringUtils;
/**
 * 自定义异常
 * @author yangzhenyu
 * */
public class MyException extends RuntimeException {
    private String code;

    public String getCode() {
        return code;
    }

    public MyException(Exception e) {
        super(e);
    }

    public MyException(ExceptionEnumService enums) {
        super(enums.getDesc());
        this.code = enums.getCode();
    }

    public MyException(ExceptionEnumService enums, String msg, String... strings) {
        super(String.format(StringUtils.isEmpty(msg) ? enums.getDesc() : msg, strings));
        this.code = enums.getCode();
    }

    public MyException(ExceptionEnumService enums, Throwable e, String... strings) {
        super(String.format(enums.getDesc(), strings), e);
        this.code = enums.getCode();
    }

    public MyException(ExceptionEnumService enums, String msg, Throwable e, String... strings) {
        super(String.format(StringUtils.isEmpty(msg) ? enums.getDesc() : msg, strings), e);
        this.code = enums.getCode();
    }
}
