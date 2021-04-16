package com.yzy.demo.test.controller;

import com.yzy.demo.test.service.FastDfsTestService;
import com.yzy.demo.test.vo.FastDfsVo;
import com.yzy.demo.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * fastDfs
 * author : yangzhenyu
 * **/
@Api(value = "FastDfs测试接口", tags = {"FastDfs测试接口"})
@RestController
@RequestMapping("api/fastDfs/")
public class FastDfsTestController {
    @Autowired
    private FastDfsTestService service;

    /**
     * 文件上传
     *@return  group1/M00/00/53/wKg8F1-ZNSCAVfYyAAAR2v9MCeo829.png
     */
    @ApiOperation(value="文件上传", notes="文件上传")
    @PostMapping(value = "/uploadFile",headers="content-type=multipart/form-data")
    public ResponseBo uploadFile(@RequestParam("file") MultipartFile file){
        return service.uploadFile(file);
    }
    /**
     * 文件文件删除
     * @Valid 校验传参
     * */
    @ApiOperation(value = "文件删除",notes = "文件删除")
    @PostMapping("/deleteByPath")
    public ResponseBo deleteByPath(@RequestBody  @Valid FastDfsVo vo){
        return service.deleteByPath(vo.getKey());
    }

    /**
     * 文件下载
     * @pram key 文件上传路径   M00/00/53/wKg8F1-ZPX-AYLUFAAAR2v9MCeo019.png
     * @pram type 下载文件类型
     * @Valid 校验传参
     * */
    @ApiOperation(value = "文件下载",notes = "文件下载/文件下载到D盘")
    @PostMapping("/fileDownload")
    public void fileDownload(@RequestBody @Valid FastDfsVo vo){
        service.fileDownload(vo.getKey(),vo.getType());
    }
}
