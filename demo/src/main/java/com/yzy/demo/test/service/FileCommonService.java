package com.yzy.demo.test.service;

import com.yzy.demo.utils.ResponseBo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 普通上传下载
 * @author yangzhenyu
 * */
public interface FileCommonService {
    /**
     * 上传文件
     * */
    public ResponseBo uploadFile(MultipartFile file);

}
