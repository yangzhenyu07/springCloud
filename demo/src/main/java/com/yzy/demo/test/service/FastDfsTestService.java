package com.yzy.demo.test.service;

import com.yzy.demo.utils.ResponseBo;
import org.springframework.web.multipart.MultipartFile;
/**
 * FastDfs
 * author : yangzhenyu
 * */
public interface FastDfsTestService {
    /**
     * 上传文件
     * */
    public ResponseBo uploadFile( MultipartFile file);

    /**
     * 上传文件删除
     * */
    public ResponseBo deleteByPath(String key);


    /**
     * 文件下载
     * */
    public void fileDownload(String key,String type);

}
