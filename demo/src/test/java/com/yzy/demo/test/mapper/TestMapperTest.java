package com.yzy.demo.test.mapper;

import com.yzy.demo.test.doMain.TestDomain;
import com.yzy.demo.test.vo.TestQuert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
@RunWith(SpringRunner.class)
public class TestMapperTest {

    private static Logger log = LoggerFactory.getLogger(TestMapperTest.class);

    private TestMapper mapper = null;

    /**传值*/
    private String INDEX = "1";
    private TestQuert vo;
    //日志信息
    private  static String DY_MSG = "";

    private final static String DY_MSG1 = "单元测试【TestMapper】 queryData";
    static{
        DY_MSG = DY_MSG1 ;
    }
    public void init(){
        String xmlPath = "/src/main/resources/config/application-Mybatis.xml";
        ApplicationContext ac = new FileSystemXmlApplicationContext(xmlPath);
        mapper = ac.getBean(TestMapper.class);
    }


    @Before
    public void setQueryUp(){
        log.info("{}--start!!!!",DY_MSG);
        vo = new TestQuert();
        vo.setId(INDEX);
    }



    @Test
    public void testQuery() {
        init();
        List<TestDomain> query = mapper.queryData(vo);
        Assert.assertNotNull(query);
    }


    /**
     *
     * 结束
     *
     * */
    @After
    public void after(){
        log.info("{}--end!!!!",DY_MSG);
    }
}