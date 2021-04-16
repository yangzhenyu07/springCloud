package com.yzy.demo.test.service;

import com.yzy.demo.test.doMain.TestDomain;
import com.yzy.demo.test.vo.TestQuert;
import com.yzy.demo.utils.ResponseBo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * @author yangzhenyu
 * */
public interface TestOneService {

    /**
     * test  service
     * */
    List<TestDomain> queryData(TestQuert vo);

    /**
     * 微服务调用测试
     * */
     ResponseBo weifuw(TestQuert vo);

    /**
     * 上传解析
     * */
    public ResponseBo test( MultipartFile file);

    /**
     * 获取自定义配置内容
     * */
    public String configProcessor(String value);
}
