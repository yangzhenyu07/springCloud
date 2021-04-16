package com.yzy.demo.test.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.yzy.demo.aop.annotation.AopTest;
import com.yzy.demo.exception.ExceptionEnum;
import com.yzy.demo.log.common.BaseLog;
import com.yzy.demo.test.doMain.TestDomain;
import com.yzy.demo.test.doMain.TestVersionDomain;
import com.yzy.demo.test.mapper.TestMapper;
import com.yzy.demo.test.mapper.TestVersionMapper;
import com.yzy.demo.test.service.TestAopTransactionalService;
import com.yzy.demo.transactional.annotation.TransactionalSelf;
import com.yzy.demo.transactional.exception.TransactionalSelfException;
import com.yzy.demo.utils.ResponseBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Aop 练习 + 自定义编辑事务[异常]练习
 * @author yangzhenyu
 * */
@Service
@DS("master")
public class TestAopTransactionalServiceImpl extends BaseLog implements TestAopTransactionalService {

    @Autowired
    private TestMapper mapper;
    private static Logger log = LoggerFactory.getLogger(TestAopTransactionalServiceImpl.class);
    @Autowired
    private TestVersionMapper testVersionMapper;


    @Override
    public long init(String name, String param) {
        this.setClazz("TestAopTransactionalServiceImpl");
        return startLog(name,param);
    }

    @AopTest(name = "注解式拦截")
    @TransactionalSelf
    public ResponseBo transactional(String value) {
        String msgValue = "noTransactional";
        long startTime = init(msgValue,value);
        //乐观锁版本号
        int version ;
        try{
            //获取乐观锁 01
            TestVersionDomain testVersionDomainStart = testVersionMapper.selectById(1);
            version = Integer.parseInt(testVersionDomainStart.getVersion());
            version++;
            TestDomain i = new TestDomain();
            i.setName(msgValue);
            i.setAge(value);
            mapper.insert(i);
            //获取乐观锁 02
            TestVersionDomain testVersionDomainEnd = testVersionMapper.selectById(1);
            if ((version-1) != Integer.parseInt(testVersionDomainEnd.getVersion())){
                //回滚
                throw new TransactionalSelfException(ExceptionEnum.BF_ERROR.getCode(),ExceptionEnum.BF_ERROR.getDesc());
            }
            //更新版本号
            TestVersionDomain versionDomain = new TestVersionDomain();
            versionDomain.setId(1L);
            versionDomain.setVersion(String.valueOf(version));
            testVersionMapper.updateById(versionDomain);
            if(value.equals("")){}
        }catch (TransactionalSelfException e){
            endLogError(msgValue,startTime,e);
            //回滚
            throw  e;
        }catch (NullPointerException e){
            endLogError(msgValue,startTime,e);
            //回滚
            throw new TransactionalSelfException(ExceptionEnum.NULL_ERROR.getCode(),ExceptionEnum.NULL_ERROR.getDesc(),e.getMessage());
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
            //回滚
            throw new TransactionalSelfException(ExceptionEnum.ERROR.getCode(),ExceptionEnum.ERROR.getDesc(),e.getMessage());
        }
        endLog(msgValue,startTime);
        return ResponseBo.ok();
    }
}
