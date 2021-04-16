package com.yzy.demo.common.client.test;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @author yangzhneyu
 * */
@Component
public class TestClientFallBackFactory   implements FallbackFactory<TestClient> {
    private static Logger log = LoggerFactory.getLogger(TestClientFallBackFactory.class);

    /**
     * 熔断对象
     */
    @Autowired
    private TestClientFallback testClientFallback;

    @Override
    public TestClient create(Throwable e) {
        log.error("调用【{}】错误", "TestClient", e);
        return testClientFallback;
    }


}
