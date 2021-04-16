package com.yzy.demo.test.service.impl;

import com.yzy.demo.aop.annotation.AopTest;
import com.yzy.demo.aop.aspect.AopTestAspect;
import com.yzy.demo.exception.throwtype.MyException;
import com.yzy.demo.utils.ResponseBo;
import com.yzy.demo.utils.file.FileService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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
@PrepareForTest({FileService.class})
@PowerMockIgnore({"javax.net.ssl.*","javax.management.*"})
public class FastDfsTestServiceImplTest {
    @InjectMocks
    private FastDfsTestServiceImpl service;


    /**
     * 单元测试补充
     *如何对这种属性进行mock[
     * @Value("${prepaid.partner.id}")
     * private String merchantPartnerId;
     * ]
     * ==>
     * ReflectionTestUtils.setField(targetObject, "merchantPartnerId", "123");
     *
     * */
    Answer<Object> defaultAnswer = new Answer<Object>() {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            System.out.println("调用 FileService.delete(key)");
            return null;
        }
    };
    @Before
    public void setUp() {
        //为UT提供框架使用的自动验证
        MockitoAnnotations.initMocks(this);
    }
    /**
     * 上传文件 正常流程
     * given file
     * when uploadFile()
     * then ResponseBo
     * */
    @Test
    public void should_return_boolean_when_uploadFile_given_file_flow0() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("test","test.txt","",new byte[64]);
        //=============start=================
        /**
         *Mock方法内部new出来的对象
         * File file = new File(path);
         * file.exists();
         * */
        //File file1 = PowerMockito.mock(File.class);
        //PowerMockito.whenNew(File.class).withArguments(file).thenReturn(file1);
        //PowerMockito.when(file1.exists()).thenReturn(true);
        //=============end=================
        //=============start=================
        /**
         * Mock系统类的静态方法
         *
         * */
        PowerMockito.mockStatic(FileService.class);
        PowerMockito.when(FileService.upload(file)).thenReturn("test.txt");
        //=============end=================
        //when
        ResponseBo responseBo =service.uploadFile(file);
        //then
        Assert.assertEquals(200,responseBo.get("code"));
    }

    /**
     * 上传文件 异常流程
     * given file
     * when uploadFile()
     * then ResponseBo
     * */
    @Test
    public void should_return_boolean_when_uploadFile_given_file_flow1() throws Exception {
        //given

        //=============start=================
        /**
         *Mock方法内部new出来的对象
         * File file = new File(path);
         * file.exists();
         * */
        //File file1 = PowerMockito.mock(File.class);
        //PowerMockito.whenNew(File.class).withArguments(file).thenReturn(file1);
        //PowerMockito.when(file1.exists()).thenReturn(true);
        //=============end=================
        //=============start=================
        /**
         * Mock系统类的静态方法
         *
         * */
        PowerMockito.mockStatic(FileService.class);
        PowerMockito.when(FileService.upload(null)).thenReturn("test.txt");
        //=============end=================

        //when
        try{
            service.uploadFile(null);
        }catch (MyException e){
            //then
            Assert.assertEquals("参数为空",e.getMessage());
            return;
        }
        service.uploadFile(null);
    }
    /**
     * 删除文件 正常流程
     * given key
     * when deleteByPath()
     * then ResponseBo
     * */
    @Test
    public void should_return_boolean_when_deleteByPath_given_key_flow0() {
        //given
        String key = "111";
        /**
         * Mock 类的void静态方法
         *
         * */
        PowerMockito.mockStatic(FileService.class,defaultAnswer);
        //PowerMockito.doNothing(FileService.delete(Mockito.anyString()));
        //when
        ResponseBo responseBo = service.deleteByPath(key);
        //then
        Assert.assertEquals(200,responseBo.get("code"));
    }

    /**
     * 删除文件 异常流程
     * given key
     * when deleteByPath()
     * then ResponseBo
     * */
    @Test
    public void should_return_boolean_when_deleteByPath_given_key_flow1() {
        //given
        String key = null;
        /**
         * Mock 类的void静态方法
         *
         * */
        PowerMockito.mockStatic(FileService.class,defaultAnswer);
        //PowerMockito.doNothing(FileService.delete(Mockito.anyString()));
        //when
        try{
            service.deleteByPath(key);
        }catch (MyException e){
            //then
            Assert.assertEquals("删除失败",e.getMessage());
            return;
        }
        //then
        service.deleteByPath(key);

    }

 
}