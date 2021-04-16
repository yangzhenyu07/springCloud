package com.yzy.demo.log;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.yzy.demo.utils.JsonUtils;
import org.springframework.util.StringUtils;
/**
 * 自定义参数解析
 * 在日志配置文件中必须指定该过滤器
 * @author yangzhenyu
 */
public class LogsFilter extends Filter<ILoggingEvent>{
    /**
     * 过滤字符串
     */
    private final static String[] replace = {"\r", "\n", "%0A", "%0a", "%0D", "%0d"};

    @Override
    public FilterReply decide(ILoggingEvent event) {
        boolean isDispose = false;
        String loggerName = event.getLoggerName();
        if (StringUtils.isEmpty(loggerName) || LogConfig.filter == null || LogConfig.filter.length == 0) {
            return FilterReply.NEUTRAL;
        }

        for (int i = 0, len = LogConfig.filter.length; i < len; i++) {
            if (loggerName.contains(LogConfig.filter[i])) {
                isDispose = true;
                break;
            }
        }
        if (!isDispose) {
            return FilterReply.NEUTRAL;
        }
        // 获取参数
        Object[] objects = event.getArgumentArray();
        if (objects == null) {
            return FilterReply.NEUTRAL;
        }
        String str = null;
        Object obj = null;
        int length = objects.length;
        // 循环解析参数并过滤字符串
        for (int i = 0; i < length; i++) {
            obj = objects[i];
            // 为空不做处理
            if (obj == null || StringUtils.isEmpty(obj)) {
                continue;
            }
            // 为异常对象不做处理
            if (Throwable.class.isAssignableFrom(obj.getClass())) {
                event.getArgumentArray()[i] = null;
                continue;
            }
            // 不是字符串的时候转换为字符串
            str = obj instanceof String ? obj.toString() : JsonUtils.toJson(obj);
            // 替换字符串可能伪造的部分
            str = replaceAll(str);

            event.getArgumentArray()[i] = str;
        }
        return FilterReply.NEUTRAL;
    }



    /**
     * 过滤字符串里面的可能引起伪造的字符串
     *
     * @return 过滤后的字符串
     */
    private String replaceAll(String str) {
        for (String re : replace) {
            str = str.replaceAll(re, "");
        }
        return str;
    }

}
