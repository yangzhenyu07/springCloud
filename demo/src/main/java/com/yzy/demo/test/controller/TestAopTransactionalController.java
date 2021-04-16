package com.yzy.demo.test.controller;

import com.yzy.demo.test.service.TestAopTransactionalService;
import com.yzy.demo.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Aop 练习 + 自定义编辑事务[异常]练习
 * @author yangzhenyu
 * */
@Api(value = "Aop+自定义编辑事务[异常]练习", tags = {"Aop+自定义编辑事务[异常]练习"})
@RestController
@RequestMapping(value="/api/aop")
public class TestAopTransactionalController {
    @Autowired
    private TestAopTransactionalService service;


    @ApiOperation(value = "Transactional")
    @CrossOrigin(origins = "*")
    @GetMapping("/transactional")
    public ResponseBo transactional(@RequestParam(value = "value",required = false) String value) {
        return service.transactional(value);
    }

}
