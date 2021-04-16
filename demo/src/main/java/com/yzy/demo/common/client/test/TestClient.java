package com.yzy.demo.common.client.test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 测试
 * @author yangzhenyu
 * */
@FeignClient(name = "test", fallbackFactory = TestClientFallBackFactory.class)
public interface  TestClient {
    /**
     * 测试
     *
     * @param id
     * @return String
     * @author yangzhenyu
     **/
    @RequestMapping(value = "/yzy/test", method = RequestMethod.POST)
    String test(String id);

}
