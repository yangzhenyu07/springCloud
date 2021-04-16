package com.yzy.demo.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.yzy.demo.common.client.test.TestClient;
import com.yzy.demo.config.processor.ConfigProcessorUtils;
import com.yzy.demo.exception.ExceptionEnum;
import com.yzy.demo.exception.throwtype.MyException;
import com.yzy.demo.log.common.BaseLog;
import com.yzy.demo.test.doMain.TestDomain;
import com.yzy.demo.test.mapper.TestMapper;
import com.yzy.demo.test.service.TestOneService;
import com.yzy.demo.test.vo.JxFileVo;
import com.yzy.demo.test.vo.TestQuert;
import com.yzy.demo.utils.ResponseBo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * test service
 * @author yangzhenyu
 * */
@Service
@DS("master")
public class TestOneServiceImpl extends BaseLog implements TestOneService {

    private static Logger log = LoggerFactory.getLogger(TestOneServiceImpl.class);



    @Override
    public long init(String name, String param) {
        this.setClazz("TestOneServiceImpl");
        return startLog(name,param);    }

    @Autowired
    private TestMapper mapper;
    /**微服务调用测试*/
    @Autowired
    private TestClient client;
    @DS("slave")
    public List<TestDomain> queryData(TestQuert vo) {
        if (StringUtils.isEmpty(vo.getId())){
            throw new MyException(ExceptionEnum.SELECT);
        }
        return mapper.queryData(vo);
    }

    /**
     * 微服务调用测试
     * */
    @Override
    public ResponseBo weifuw(TestQuert vo) {
        if (StringUtils.isEmpty(vo.getId())){
            throw new MyException(ExceptionEnum.SELECT);
        }
        String test = client.test(vo.getId());
        if(null == test){
            return ResponseBo.error(ExceptionEnum.WFUERROR);
        }
        return ResponseBo.ok(test);
    }

    /**
     * 流转出字符串 第二种方法
     *
     * */
    public String  inputStreamToStringTest(InputStream is) throws UnsupportedEncodingException {
        InputStreamReader isr =  new InputStreamReader(is, "UTF-8");
        StringBuilder sb = new StringBuilder();
        try {
            char buf[] = new char[20];
            int nBufLen = isr.read(buf);
            while(nBufLen!= -1){
                sb.append(new String(buf,0,nBufLen));
                nBufLen = isr.read(buf);
            }
        } catch (IOException e) {
            log.error("流转字符串出错", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error("解析文件关流出错", e);
            }
        }
        return sb.toString();
    }
    /**
     * 流转出字符串
     * */
    public String  inputStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            log.error("流转字符串出错", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error("解析文件关流出错", e);
            }
        }
        return sb.toString();
    }

    /**
     * 去换行
     * */
    public  String replaceBlank(String str){
        String dest = "";
        if (str!= null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 去多余空格
     * */
    public  String qkg(String str){
        String dest = "";
        if (str!=null) {
            dest =        str.replaceAll(" +", " ");
            return dest;
        }else {
            return dest;
        }
    }
    @Override
    public ResponseBo test(MultipartFile file) {
        String msgValue = "test";
        long startTime = init(msgValue,file.getName());
        if(null == file){
            log.error("解析文件不允许为空!!!");
            endLog(msgValue,startTime);
            throw new MyException(ExceptionEnum.NULL);
        }
        InputStream input = null;
        String fileExtName = null;
        String filename = null;
        JxFileVo jxFileVo = null;
        try{
            input = file.getInputStream();
            filename = file.getOriginalFilename();
            fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
            if (!"BDM".equalsIgnoreCase(fileExtName)) {
                return ResponseBo.error("模板文件不对，请使用正确的模板导入");
            }
            String jsonMb = inputStreamToString(input);
            jsonMb = qkg(replaceBlank(jsonMb));
            log.info("-------------------[{}]-------",jsonMb);
            jxFileVo = JSON.parseObject(jsonMb,new TypeReference<JxFileVo>(){
            });
            endLog(msgValue,startTime);
        }catch (Exception e){
            log.error("JSON转VO出错", e);
            endLogError(msgValue,startTime,e);
        }finally {
            try{
                input.close();
            }catch (Exception e){
                log.error("解析文件关流出错", e);
            }
        }
        return ResponseBo.ok(jxFileVo);
    }

    /**
     * 获取自定义配置内容
     * */
    public String configProcessor(String value) {
        return ConfigProcessorUtils.getPropertiesBySingleKey(value);
    }
}
