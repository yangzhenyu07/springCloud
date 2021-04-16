package com.yzy.demo.common.client.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * @author yangzhneyu
 * */
@Component
public class TestClientFallback implements TestClient {

    private static Logger log = LoggerFactory.getLogger(TestClientFallback.class);

    /**
     * 容错处理
     */
    @Override
    public String test(String id) {
        log.error("调用测试接口异常，传参:【{}】", id);
        //断路由模式，帮助服务依赖中出现的延迟和为故障提供强大的容错机制
        return null;
    }


}
