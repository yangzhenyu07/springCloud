package com.yzy.demo.transactional.aspect;

import com.yzy.demo.transactional.exception.TransactionalSelfException;
import com.yzy.demo.utils.ResponseBo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 编辑自定义事务控制注解
 * @author yangzhenyu
 * */
@Aspect
@Component
@EnableAspectJAutoProxy(exposeProxy = true,proxyTargetClass = true)
public class TransactionalSelfAspect {

    private static Logger log = LoggerFactory.getLogger(TransactionalSelfAspect.class);
    @Pointcut("@annotation(com.yzy.demo.transactional.annotation.TransactionalSelf)")
    public void pointcut(){};
    //事务处理类
    @Autowired
    private DataSourceTransactionManager manager;

    /**事务切面处理方法
     * */
    @Around("pointcut()")
    public Object cut(ProceedingJoinPoint point) throws Throwable{
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //如果当前没有事务，就新建一个事务，如果已经存在事务，就加入当前事务
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); //事务传播行为
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        TransactionStatus status = manager.getTransaction(def);
        Object result = null;
        try{
            result = point.proceed();
            //提交事务
            manager.commit(status);
        }catch (Exception e){
            //回滚事务
            manager.rollback(status);
            log.error("==========================回滚事务=================================");
            //抓取自定义异常封装返回
            if (e instanceof TransactionalSelfException){
                TransactionalSelfException tr = (TransactionalSelfException)e;
                return   ResponseBo.error(tr.getErrorCode(),tr.getErrorInfo(),tr.getErrorMessage());
            }
        }
        return result;
    }
}
