package com.yzy.demo.test.controller;

import com.yzy.demo.common.client.test.TestClient;
import com.yzy.demo.common.token.ScToken;
import com.yzy.demo.test.doMain.TestDomain;
import com.yzy.demo.test.service.TestOneService;
import com.yzy.demo.test.vo.TestQuert;
import com.yzy.demo.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangzhenyu
 * */
@Api(value = "测试服务", tags = {"测试服务"})
@RestController
@RequestMapping(value="api/test")
public class TestController {

    @Autowired
    private TestOneService service;

    @ApiOperation(value = "get测试接口")
    @CrossOrigin(origins = "*")
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @ApiOperation(value = "post测试接口")
    @CrossOrigin(origins = "*")
    @PostMapping("/post")
    public List<TestDomain> test(@RequestBody TestQuert vo){
        return service.queryData(vo);
    }



    @ApiOperation(value = "获取自定义配置内容")
    @CrossOrigin(origins = "*")
    @GetMapping("/configProcessor")
    public ResponseBo configProcessor(@RequestParam(value = "value", defaultValue = "xxx.hhh") String value) {
        return ResponseBo.ok(service.configProcessor(value));
    }

    @ApiOperation(value = "微服务调用测试接口")
    @CrossOrigin(origins = "*")
    @PostMapping("/weifuw")
    public ResponseBo weifuw(@RequestBody TestQuert vo){
        return service.weifuw(vo);
    }

    /**
     * 文件上传
     *@return  group1/M00/00/53/wKg8F1-ZNSCAVfYyAAAR2v9MCeo829.png
     */
    @ApiOperation(value="文件解析", notes="文件解析")
    @PostMapping(value = "/uploadFile",headers="content-type=multipart/form-data")
    public ResponseBo uploadFile(@RequestParam("file") MultipartFile file){
        return service.test(file);
    }
}
