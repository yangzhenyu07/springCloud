package com.yzy.demo.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yzy.demo.eventdrive.LoginProducer;
import com.yzy.demo.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yangzhenyu
 * session 练习
 * */
@Api(value = "session 练习", tags = {"session 练习"})
@RestController
@RequestMapping(value="/api/session")
public class SessionController {
    @Autowired
    private LoginProducer loginProducer;
    /**
     * 登录
     * */
    @ApiOperation(value = "登录",notes = "生成sessionId 到前段cookie")
    @ResponseBody
    @PostMapping("/login")
    public ResponseBo login(HttpSession session) throws JsonProcessingException {
        String msg = "";
        //向域对象中添加数据
        session.setAttribute("userId","yangzhenyu");
        //设置最大不活动时间，达到时间，销毁
        session.setMaxInactiveInterval(6000);
        //判断session是不是新的
        //session.getAttribute("userId") 从域对象中取数据
        //===========================================================
        //事件驱动模型测试
        loginProducer.sendMsg("yangzhenyu");
        //===========================================================
        if (session.isNew()){
            msg = "login success!\n userId:"+session.getAttribute("userId")+"\n"+"session id:"+session.getId();
            return ResponseBo.ok(msg);
        }else {
            msg = "login success!\n 【服务器已经存在该session】userId:"+session.getAttribute("userId")+"\n"+"session id:"+session.getId();
            return ResponseBo.ok(msg);
        }
    }
    /**
     * 调用练习
     * */
    @ApiOperation(value = "调用练习",notes = "生成sessionId 到前段cookie")
    @ResponseBody
    @PostMapping("/user")
    public ResponseBo user(HttpServletRequest request){
        HttpSession session = request.getSession();
        String msg = "";
        //从域对象中取数据
        session.getAttribute("userId");
        //判断session是不是新的
        if (session.isNew()){
            msg = "user success!\n userId:"+session.getAttribute("userId")+"\n"+"session id:"+session.getId();
            return ResponseBo.ok(msg);
        }else {
            msg = "user success!\n 【服务器已经存在该session】userId:"+session.getAttribute("userId")+"\n"+"session id:"+session.getId();
            return ResponseBo.ok(msg);
        }
    }
    /**
     * 退出
     * */
    @ApiOperation(value = "退出",notes = "强制销毁该sessionId ")
    @ResponseBody
    @PostMapping("/off")
    public ResponseBo off(HttpSession session){
        String msg = "";
        //强制销毁该session
        session.invalidate();
        msg = "off success";
        return ResponseBo.ok(msg);
    }

}
