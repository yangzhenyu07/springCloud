package com.yzy.demo.exception;
/**
 * @author yangzhenyu
 * */
public enum ExceptionEnum implements ExceptionEnumService{
    SELECT("SELECT","查询失败"),
    UPDATE("UPDATE","修改失败"),
    WFUERROR("WFUERROR","微服务熔断处理"),
    NULL("NULL","参数为空"),
    CONFIG("CONFIG","核心配置错误"),
    UPLOAD("UPLOAD","上传失败"),
    DELETE("DELETE","删除失败"),
    DOWNLOAD("DOWNLOAD","下载失败"),
    SYSCONFIG("SYSCONFIG","系统配置错误"),
    PARAM_ERROR("PARAM_ERROR","传参错误"),
    NULL_ERROR("NULL_ERROR","空指针错误"),
    ERROR("ERROR","其他错误"),
    SPRING_EVENT_ERROR("SPRING_EVENT_ERROR","Spring 事件监听 失败"),
    DISTRIBUTED_SEESION_ERROR("DISTRIBUTED_SEESION_ERROR","分布式会话出现错误 失败"),

    BF_ERROR("BF_ERROR","并发冲突");



    /**
     * 枚举编码
     */
    private String code;
    /**
     * 枚举说明
     */
    private String desc;

    ExceptionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return  this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
