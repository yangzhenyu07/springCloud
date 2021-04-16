package com.yzy.demo.transactional.exception;
/**
 * 编辑自定义事务配置异常类
 * @author yangzhenyu
 * */
public class TransactionalSelfException extends RuntimeException{

    /**
     * 错误码
     * */
    private String errorCode;
    /**
     * 错误描述
     * */
    private String errorMessage;
    /**
     * 错误信息
     * */
    private String errorInfo;
    public TransactionalSelfException(){
        super();
    }
    public TransactionalSelfException(String errorCode,  String errorMessage,String errorInfo) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.errorMessage = errorMessage;
    }

    public TransactionalSelfException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public TransactionalSelfException(String errorCode, String errorMessage,Throwable e) {
        super(errorMessage,e);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
