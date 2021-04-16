package com.yzy.demo.test.controller;

import com.yzy.demo.common.token.JxToken;
import com.yzy.demo.common.token.ScToken;
import com.yzy.demo.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/**
 * @author yangzhenyu
 * */
@Api(value = "token生成服务", tags = {"token生成服务"})
@RestController
@RequestMapping(value="/token")
public class TokenController extends JxToken {

    @Autowired
    private HttpServletRequest request;
    @ApiOperation(value = "token生成接口")
    @CrossOrigin(origins = "*")
    @GetMapping("/init")
    public ResponseBo hello(@RequestParam(value = "name", defaultValue = "yangzhenyu") String name, @RequestParam(value = "age", defaultValue = "26") String age) {
        ScToken scToken = new ScToken();
        Map<String,String> map = new HashMap<>();
        map.put("name",name);
        map.put("age",age);
        return ResponseBo.ok(scToken.initToken(map));
    }

    @ApiOperation(value = "解析token接口")
    @CrossOrigin(origins = "*")
    @GetMapping("/jx")
    public ResponseBo jx() {
        Map<String, Object> info = getInfo(request);
        return ResponseBo.ok(info);
    }


}
