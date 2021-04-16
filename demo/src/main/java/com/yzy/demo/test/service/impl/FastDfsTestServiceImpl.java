package com.yzy.demo.test.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.yzy.demo.aop.annotation.AopTest;
import com.yzy.demo.exception.ExceptionEnum;
import com.yzy.demo.exception.throwtype.MyException;
import com.yzy.demo.log.common.BaseLog;
import com.yzy.demo.test.service.FastDfsTestService;
import com.yzy.demo.utils.ResponseBo;
import com.yzy.demo.utils.file.FileService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * FastDfs
 * author : yangzhenyu
 * */
@Service
@DS("master")
public class FastDfsTestServiceImpl extends BaseLog implements FastDfsTestService {

    private static Logger log = LoggerFactory.getLogger(FastDfsTestServiceImpl.class);



    @Override
    public long init(String name, String param) {
        this.setClazz("FastDfsTestServiceImpl");
        return startLog(name,param);
    }


    /**
     * 上传文件
     * @return  group1/M00/00/53/wKg8F1-ZNSCAVfYyAAAR2v9MCeo829.png
     * */
    public ResponseBo uploadFile(MultipartFile file) {
        String msgValue = "uploadFile";
        long startTime = 0L;
        if (null == file){
             startTime = init(msgValue,null);
        }else {
             startTime = init(msgValue,file.getName());
        }
        if(null == file){
            log.error("文件不允许为空!!!");
            endLog(msgValue,startTime);
            throw new MyException(ExceptionEnum.NULL);
        }
        String upload = null;
        try {
            upload = FileService.upload(file);
            endLog(msgValue,startTime);
        } catch (Exception e) {
            log.error("上传文件服务器失败！",e);
            endLogError(msgValue,startTime,e);
            throw new MyException(ExceptionEnum.UPLOAD);
        }
        return ResponseBo.ok(upload);
    }

    /**
     * 上传文件删除
     * M00/00/53/wKg8F1-ZPX-AYLUFAAAR2v9MCeo019.png
     * */
    @AopTest(name = "deleteByPath")
    public ResponseBo deleteByPath(String key) {
        String msgValue = "deleteByPath";
        long startTime = startLog(msgValue,key);
        try{
            if(StringUtils.isEmpty(key)){
                log.error("key为空!!!");
                endLog(msgValue,startTime);
                throw new MyException(ExceptionEnum.NULL);
            }
            FileService.delete(key);
            endLog(msgValue,startTime);
        }catch (Exception e) {
            log.error("上传文件删除失败！",e);
            endLogError(msgValue,startTime,e);
            throw new MyException(ExceptionEnum.DELETE);

        }
        return ResponseBo.ok("删除成功");
    }

    /**
     * 文件下载
     * M00/00/53/wKg8F1-ZPX-AYLUFAAAR2v9MCeo019.png
     * */
    public void fileDownload(String key,String type) {
        String msgValue = "fileDownload";
        long startTime = startLog(msgValue,"key："+key+"type:"+type);
        try{
            if(StringUtils.isEmpty(key)){
                log.error("key为空!!!");
                endLog(msgValue,startTime);
                throw new MyException(ExceptionEnum.NULL);
            }
            FileService.download(key,type);
            endLog(msgValue,startTime);
        }catch (Exception e) {
            log.error("文件下载失败！",e);
            endLogError(msgValue,startTime,e);
            throw new MyException(ExceptionEnum.DOWNLOAD);

        }
    }
}
