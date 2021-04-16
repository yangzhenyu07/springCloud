package com.yzy.demo.utils.file;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
/**
 * file 类
 * author : yangzhenyu
 * */
public interface FileBase {
    /**
     * 下载
     * */
    void download(String cloudFilePath,String type);

    /**
     * 上传
     * */
    String upload(MultipartFile file);

    /**
     * 删除
     * */
    void delete(String key);

}
