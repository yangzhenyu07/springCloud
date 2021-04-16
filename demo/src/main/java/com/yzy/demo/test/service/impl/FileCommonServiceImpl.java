package com.yzy.demo.test.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.yzy.demo.exception.ExceptionEnum;
import com.yzy.demo.exception.throwtype.MyException;
import com.yzy.demo.log.common.BaseLog;
import com.yzy.demo.test.service.FileCommonService;
import com.yzy.demo.utils.ResponseBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 普通上传下载
 * @author yangzhenyu
 * */
@Service
@DS("master")
public class FileCommonServiceImpl extends BaseLog implements FileCommonService {
    private static Logger log = LoggerFactory.getLogger(FileCommonServiceImpl.class);
    @Override
    public long init(String name, String param) {
        this.setClazz("FileCommonServiceImpl");
        return startLog(name,param);    }

   /**
    * 上传文件
    * */
    public ResponseBo uploadFile(MultipartFile file) {
        String msgValue = "uploadFile";
        long startTime = init(msgValue,file.getName());
        if(null == file){
            log.error("文件不允许为空!!!");
            endLog(msgValue,startTime);
            throw new MyException(ExceptionEnum.NULL);
        }
        try {
            File newFile = new File("D://work/"+file.getOriginalFilename());
            file.transferTo(newFile);
            endLog(msgValue,startTime);
        } catch (Exception e) {
            log.error("普通上传文件失败！",e);
            endLogError(msgValue,startTime,e);
            throw new MyException(ExceptionEnum.UPLOAD);
        }
        endLog(msgValue,startTime);
        return ResponseBo.ok();
    }



    /**
     * 文件下载
     * */
    /**
    public ResponseBo download()  {
        String msgKey = "FileCommonServiceImpl";
        String msgValue = "download";
        long startTime = startLog(msgKey,msgValue);
        String fileName = "application.yml";
        File file = null;
        try{
            file = ResourceUtils.getFile("classpath:"+fileName);
            return download(file,startTime);
        }catch (Exception e){
            log.error("普通文件下载失败！",e);
            endLogError("类["+msgKey+"],方法["+msgValue+"]",startTime,e);
            throw new MyRunTimeException(ExceptionEnum.DOWNLOAD);
        }

    }
    public ResponseBo download(File file,long startTime) throws FileNotFoundException {
        String msgKey = "FileCommonServiceImpl";
        String msgValue = "download";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition",String.format("attachment; filename="+file.getName()));
        httpHeaders.add("Pragma","no-cache");
        httpHeaders.add("Expires","0");
        httpHeaders.add("Cache-Control","no-cache,no-store,must-revaliadate");
        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok().headers(httpHeaders)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new FileInputStream(file)));
        endLog("类["+msgKey+"],方法["+msgValue+"]",startTime);
        return ResponseBo.ok(responseEntity);
    }*/
}
