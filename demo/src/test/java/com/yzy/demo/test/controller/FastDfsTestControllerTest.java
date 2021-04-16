package com.yzy.demo.test.controller;

import com.yzy.demo.test.service.FastDfsTestService;
import com.yzy.demo.test.vo.FastDfsVo;
import com.yzy.demo.utils.ResponseBo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockMultipartFile;

/**
 * 单元测试
 * author : yangzhenyu
 * **/
@RunWith(PowerMockRunner.class)
@PrepareForTest({})
@PowerMockIgnore({"javax.net.ssl.*","javax.management.*"})
public class FastDfsTestControllerTest {
    @InjectMocks
    private FastDfsTestController fastDfsTestController;
    @Mock
    private FastDfsTestService service;

    @Before
    public void setUp() {
        //为UT提供框架使用的自动验证
        MockitoAnnotations.initMocks(this);
    }
    /**
     * 文件上传
     *@return  group1/M00/00/53/wKg8F1-ZNSCAVfYyAAAR2v9MCeo829.png
     */
    @Test
    public void uploadFile() {
        MockMultipartFile file = new MockMultipartFile("test","test.txt","",new byte[64]);
        PowerMockito.when(service.uploadFile(file)).thenReturn(ResponseBo.ok("group1/M00/00/53/wKg8F1-ZNSCAVfYyAAAR2v9MCeo829.png"));
        ResponseBo responseBo = fastDfsTestController.uploadFile(file);
        //Assert.assertTrue(true);
        Assert.assertEquals(200,responseBo.get("code"));
    }
    /**
     * 文件文件删除
     * @Valid 校验传参
     * */
    @Test
    public void deleteByPath() {
        FastDfsVo vo = new FastDfsVo();
        PowerMockito.when(service.deleteByPath(Mockito.anyString())).thenReturn(ResponseBo.ok("group1/M00/00/53/wKg8F1-ZNSCAVfYyAAAR2v9MCeo829.png"));
        ResponseBo responseBo = fastDfsTestController.deleteByPath(vo);
        Assert.assertEquals(200,responseBo.get("code"));

        vo.setKey("454"); //key值
        ResponseBo responseBo1 = fastDfsTestController.deleteByPath(vo);
        Assert.assertEquals(200,responseBo1.get("code"));

        vo.setType("txt"); //文件类型
        ResponseBo responseBo2 = fastDfsTestController.deleteByPath(vo);
        Assert.assertEquals(200,responseBo2.get("code"));
    }

}