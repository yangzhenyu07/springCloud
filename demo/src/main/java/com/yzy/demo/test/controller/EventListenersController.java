package com.yzy.demo.test.controller;

import com.yzy.demo.eventlisteners.CreateOrderEvent;
import com.yzy.demo.exception.ExceptionEnum;
import com.yzy.demo.exception.throwtype.MyException;
import com.yzy.demo.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring 事件监听
 * author : yangzhenyu
 * **/
@Api(value = "spring 事件监听测试接口", tags = {"spring 事件监听测试接口"})
@RestController
@RequestMapping("api/eventListeners/")
public class EventListenersController {
    @Autowired
    private CreateOrderEvent event;
    /**
     * spring 事件监听
     *@return
     */
    @ApiOperation(value="spring 事件监听", notes="spring 事件监听")
    @PostMapping(value = "/eventListeners")
    public ResponseBo eventListeners(@RequestParam(value = "value", defaultValue = "杨镇宇") String value){
        try{
            event.createEnent(value);
        }catch (Exception e){
            throw new MyException(ExceptionEnum.SPRING_EVENT_ERROR);
        }
        return ResponseBo.ok();
    }
}
