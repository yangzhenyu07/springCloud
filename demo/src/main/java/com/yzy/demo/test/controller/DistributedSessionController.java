package com.yzy.demo.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzy.demo.exception.ExceptionEnum;
import com.yzy.demo.exception.throwtype.MyException;
import com.yzy.demo.log.common.BaseLog;
import com.yzy.demo.session.annotation.MySession;
import com.yzy.demo.test.vo.User;
import com.yzy.demo.test.vo.distributed.DistributedSessionVo;
import com.yzy.demo.utils.DateUtils;
import com.yzy.demo.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式会话测试类
 * author : yangzhenyu
 * **/
@Api(value = "分布式会话/缓存测试接口", tags = {"分布式会话/缓存测试接口"})
@RestController
@RequestMapping("api/distributed/")
public class DistributedSessionController  extends BaseLog {
    //缓存的超时时间
    @Value("${config.session.timeOut:5}")
    private long timeOut;
    //分布式 sessionId 的超时时间
    @Value("${config.session.sessionTimeOut:5}")
    private long sessionTimeOut;
    //缓存中的命名前缀【防缓存雪崩设计】
    @Value("${config.session.head:yzy_}")
    private String keyHead;
    //存放cookie的sessionId的key值
    @Value("${config.session.sessionKey:SESSION_KEY}")
    private String sessionKey;
    /**
     * json 序列化和反序列化框架类
     *  String s = objectMapper.writeValueAsString(对象);
     *  对象 对象 = objectMapper.readValue("{}",对象.class);
     * */
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public long init(String name, String param) {
        this.setClazz("DistributedSessionController");
        return startLog(name,param);
    }
    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 模拟查询数据库
     * */
    public DistributedSessionVo query(String id){
        DistributedSessionVo distributedSessionVo = new DistributedSessionVo();
        distributedSessionVo.setName("分布式会话实战书籍");
        distributedSessionVo.setNumber("12");
        distributedSessionVo.setId(id);
        return distributedSessionVo;
    }
    /**
     * 查询热销商品
     *  @return
     */
    @ApiOperation(value="查询热销商品【防缓存雪崩设计】", notes="查询热销商品【防缓存雪崩设计】:")
    @GetMapping(value = "/queryCommodity/{id}")
    public ResponseBo queryCommodity(@PathVariable  String id) throws JsonProcessingException {
        String msgValue = "queryCommodity";
        long startTime = init(msgValue,id);
        //不加前缀key值
        final String key =  "commodity_"+id;
        //加前缀key值
        final String headKey = keyHead + key;
        try{
            ValueOperations valueOperations = redisTemplate.opsForValue();
            if (redisTemplate.hasKey(headKey)){
                //加前缀key值缓存存在，从缓存中获取内容返回
                endLog(msgValue,startTime);
                return ResponseBo.ok( objectMapper.readValue(valueOperations.get(headKey).toString(),DistributedSessionVo.class));
            }else{
                if (redisTemplate.hasKey(key)){
                    endLog(msgValue,startTime);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //查询数据库
                            DistributedSessionVo query = query(id);
                            try {
                                if (null == query){
                                    //【防缓存雪崩设计】 防缓存穿透设置】
                                    valueOperations.set(key,objectMapper.writeValueAsString(new DistributedSessionVo()));
                                    valueOperations.set(headKey,objectMapper.writeValueAsString(new DistributedSessionVo()));
                                    //设置过期时间
                                    //【防缓存雪崩设计】 防缓存穿透设置】
                                    redisTemplate.expire(key,timeOut*2, TimeUnit.MINUTES);
                                    redisTemplate.expire(headKey,timeOut, TimeUnit.MINUTES);
                                }else{
                                    //【防缓存雪崩设计】
                                    valueOperations.set(key,objectMapper.writeValueAsString(query));
                                    valueOperations.set(headKey,objectMapper.writeValueAsString(query));

                                    //设置过期时间
                                    //【防缓存雪崩设计】
                                    redisTemplate.expire(key,timeOut*2, TimeUnit.MINUTES);
                                    redisTemplate.expire(headKey,timeOut, TimeUnit.MINUTES);
                                }
                            }catch (Exception e){
                                error(String.format("缓存更新错误【%s】,错误:%s",key,e.getMessage()));
                            }
                        }
                    }).start();
                    return ResponseBo.ok( objectMapper.readValue(valueOperations.get(key).toString(),DistributedSessionVo.class));
                }
                //=============================【防缓存穿透设置】===========================
                //查询数据库
                DistributedSessionVo query = query(id);
                //放入缓存，并返回
                if (null == query){
                    //【防缓存雪崩设计】 防缓存穿透设置】
                    valueOperations.set(key,objectMapper.writeValueAsString(new DistributedSessionVo()));
                    valueOperations.set(headKey,objectMapper.writeValueAsString(new DistributedSessionVo()));
                    //设置过期时间
                    //【防缓存雪崩设计】 防缓存穿透设置】
                    redisTemplate.expire(key,timeOut*2, TimeUnit.MINUTES);
                    redisTemplate.expire(headKey,timeOut, TimeUnit.MINUTES);
                }else{
                    //【防缓存雪崩设计】
                    valueOperations.set(key,objectMapper.writeValueAsString(query));
                    valueOperations.set(headKey,objectMapper.writeValueAsString(query));

                    //设置过期时间
                    //【防缓存雪崩设计】
                    redisTemplate.expire(key,timeOut*2, TimeUnit.MINUTES);
                    redisTemplate.expire(headKey,timeOut, TimeUnit.MINUTES);
                }
                endLog(msgValue,startTime);
                return ResponseBo.ok(query);
            }
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
            throw new MyException(ExceptionEnum.DISTRIBUTED_SEESION_ERROR);
        }

    }
    /**
     * 获取热销商品id
     *  @return
     */
    @ApiOperation(value="获取热销商品id【防缓存雪崩设计】", notes="获取热销商品信息:模拟从数据库中查出相关数据，放入缓存，并返回商品id")
    @PostMapping(value = "/initCommodity")
    public ResponseBo initCommodity() throws JsonProcessingException {
        String msgValue = "initCommodity";
        /**
         * 【缓存雪崩】是由于原有缓存失效(过期)，新缓存未到期间。所有请求都去查询数据库，
         *  而对数据库CPU和内存造成巨大压力，严重的会造成数据库宕机。从而形成一系列连锁反应，造成整个系统崩溃
         * */
        Map<String,String> map = new HashMap<>();
        long startTime = init(msgValue,null);
        try{
            //start 模拟db 查询 ======================================
            DistributedSessionVo distributedSessionVo = new DistributedSessionVo();
            distributedSessionVo.setName("分布式会话实战书籍");
            distributedSessionVo.setNumber("12");
            distributedSessionVo.setId("12345677899902321");
            String commodity = "commodity_"+ distributedSessionVo.getId();
            String key = keyHead+commodity;
            //end 模拟db 查询  =======================================
            ValueOperations valueOperations = redisTemplate.opsForValue();
            //【防缓存雪崩设计】
            valueOperations.set(key,objectMapper.writeValueAsString(distributedSessionVo));
            valueOperations.set(commodity,objectMapper.writeValueAsString(distributedSessionVo));

            //设置过期时间
            //【防缓存雪崩设计】
            redisTemplate.expire(key,timeOut, TimeUnit.MINUTES);
            redisTemplate.expire(commodity,timeOut*2, TimeUnit.MINUTES);
            map.put("商品id",distributedSessionVo.getId());
            endLog(msgValue,startTime);
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
            throw new MyException(ExceptionEnum.DISTRIBUTED_SEESION_ERROR);
        }
        return ResponseBo.ok(map);
    }

    /**
     * 分布式会话测试
     * */
    @ApiOperation(value="分布式会话测试", notes="分布式会话测试")
    @PostMapping(value = "/sessionTest")
    @MySession
    public ResponseBo sessionTest(Map session){
        if (null == session.get("userId")){
            return ResponseBo.ok("会话已过期！！！");
        }
        return ResponseBo.ok(session);
    }
    /**
     * 获取sessionId
     *  @return
     */
    @ApiOperation(value="生成分布式sessionID,并存入cookie", notes="案例:模拟从数据库中查出相关数据，放入缓存，并将sessionId存入cookie")
    @PostMapping(value = "/initSessionId")
    public ResponseBo initSessionId() throws JsonProcessingException {
        String msgValue = "initSessionId";
        Map<String,String> map = new HashMap<>();
        long startTime = init(msgValue,null);
        try{
            //start 模拟db 查询 ======================================
            User user = new User();
            user.setName("杨镇宇");
            user.setUserId("122221");
            String sessionId = "session_"+simpleUUID();
            String key = keyHead+sessionId;
            //end 模拟db 查询  =======================================
            ValueOperations valueOperations = redisTemplate.opsForValue();
            /**
             * 设计思路:
             * 当缓存中无【sessionId】，说明已过期
             * */
            valueOperations.set(key,objectMapper.writeValueAsString(user));
            //设置过期时间
            redisTemplate.expire(key,sessionTimeOut, TimeUnit.MINUTES);
            //存放cookie
            HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
            setCookie(response,sessionKey,key);
            endLog(msgValue,startTime);
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
            throw new MyException(ExceptionEnum.DISTRIBUTED_SEESION_ERROR);
        }
        return ResponseBo.ok();
    }

    /**
     * 存放cookie中
     * */
    public void setCookie(HttpServletResponse response,String key,String sessionId){
         if (null ==response){
             return;
         }
         if (null == key || null == sessionId || sessionId.isEmpty()){
             return;
         }
         Cookie cookie = new Cookie(key,sessionId);
         cookie.setPath("/");
         response.addCookie(cookie);
    }
    /**
     * 从cookie中获取sessionId
     * */
    public String getCookie(HttpServletRequest request, String key){
        if (request == null){
            return null;
        }
        if (null == key||key.isEmpty()){
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (null == cookies){
            return null;
        }
        for (Cookie cookie:cookies){
            if (key.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
