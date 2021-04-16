package com.yzy.demo.test.controller;

import com.yzy.demo.test.service.FileCommonService;
import com.yzy.demo.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 普通上传下载
 * @author yangzhenyu
 * */
@Api(value = "普通上传下载 练习", tags = {"普通上传下载 练习"})
@RestController
@RequestMapping(value="/api/commonFile")
public class FileCommonController {
    @Autowired
    private FileCommonService service;
    /**
     * 文件上传
     *
     */
    @ApiOperation(value="文件上传", notes="文件上传")
    @PostMapping(value = "/uploadFile",headers="content-type=multipart/form-data")
    public ResponseBo uploadFile(@RequestParam("file") MultipartFile file){
        return service.uploadFile(file);
    }

}
