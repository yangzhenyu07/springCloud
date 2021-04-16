package com.yzy.demo.utils.file;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


public class TestF implements FileBase{

    public void download(HttpServletResponse response, String cloudFilePath, String type) {

    }

    @Override
    public void download(String cloudFilePath, String type) {

    }

    @Override
    public String upload(MultipartFile file) {
        return null;
    }

    @Override
    public void delete(String key) {

    }
}
