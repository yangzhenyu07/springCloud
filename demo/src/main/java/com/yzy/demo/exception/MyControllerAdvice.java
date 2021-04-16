package com.yzy.demo.exception;

import com.yzy.demo.exception.throwtype.MyException;
import com.yzy.demo.utils.ResponseBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 全局异常拦截
 * @author yangzhenyu
 * */
@ControllerAdvice
@ConditionalOnExpression("${config.exception.switch:true} == true")
public class MyControllerAdvice {

    private static Logger log = LoggerFactory.getLogger(MyControllerAdvice.class);

    public MyControllerAdvice() {
        log.info("===================全局异常拦截配置===================");
    }

    /**
     * 全局传参校验异常抓取
     * */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseBo bingException(MethodArgumentNotValidException e, HttpSession session){
        return ResponseBo.error(ExceptionEnum.PARAM_ERROR.getCode(),ExceptionEnum.PARAM_ERROR.getDesc(), e.getBindingResult().getFieldError().getDefaultMessage());
    }
    /**
     * 全局异常处理
     *
     * */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public ResponseBo exceptionHandler(Exception e, HttpSession session) {
        return returnData(e);
    }

    /**
     * 组装错误返回参数
     * */
    public ResponseBo returnData(Throwable e){
        ResponseBo responseBo = null;
        try{
            responseBo =  getResultData(e,e.getClass());
            if (responseBo == null) {
                Object obj = e.getCause();
                if (obj != null) {
                    responseBo = getResultData(e, obj.getClass());
                }
            }
            if (responseBo == null || responseBo.get("code") == null || ExceptionConfig.code.equalsIgnoreCase((String) responseBo.get("code"))) {
                if (responseBo == null) {
                    responseBo = ResponseBo.error(ExceptionConfig.code, ExceptionConfig.info);
                } else if (responseBo.get("code") == null ) {
                    responseBo.put("code",ExceptionConfig.code);
                } else if (responseBo.get("msg") == null ) {
                    responseBo.put("msg",ExceptionConfig.info);
                }
            } else {
                    log.error("业务异常信息捕获成功,业务码:【{}】，业务提示信息:【{}】", responseBo.get("code"), responseBo.get("msg"), e);
            }
        }catch (Throwable ee){
            log.error("统一异常处理错误:", ee);
            ee = null;
        }
        e = null;
        return responseBo;
    }

    private ResponseBo getResultData(Object e, Class clz) {
        if (clz != null) {
            //class1.isAssignableFrom(class2) 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口。如果是则返回 true；否则返回 false
            if (MyException.class.isAssignableFrom(clz)) {
                MyException ex = (MyException) e;
                return ResponseBo.error(ex.getCode(), ex.getMessage());
            }
        }
        return null;
    }

}
