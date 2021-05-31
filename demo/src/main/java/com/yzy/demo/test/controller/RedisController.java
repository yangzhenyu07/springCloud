package com.yzy.demo.test.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzy.demo.log.common.BaseLog;
import com.yzy.demo.test.vo.*;
import com.yzy.demo.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * author : yangzhenyu
 * **/
@Api(value = "redis测试接口", tags = {"redis测试接口"})
@RestController
@RequestMapping("api/redis/")
public class RedisController extends BaseLog {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * json 序列化和反序列化框架类
     *  String s = objectMapper.writeValueAsString(对象);
     *  对象 对象 = objectMapper.readValue("{}",对象.class);
     * */
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * StringRedisTemplate 为 RedisTemplate的特例，专门处理缓存中value的数据类型为字符串String的数据，包括String类型的数据
     * 和序列化为String类型的字符串数据
     * */
    @Autowired
    private  StringRedisTemplate stringRedisTemplate;

    @Override
    public long init(String name, String param) {
        this.setClazz("RedisController");
        return startLog(name,param);
    }

    /**
     * Redis分布式锁设计
     * */
    @ApiOperation(value = "Redis分布式锁设计",notes = "Redis分布式锁设计")
    @ResponseBody
    @PostMapping("/lock")
    public ResponseBo lock(String key)  {
        String msgValue = "lock";
        long startTime = init(msgValue,key);
        ValueOperations stringStringValueOperations = stringRedisTemplate.opsForValue();
        //value值 纳秒级时间戳+随机数
        final String value = System.nanoTime()+""+ UUID.randomUUID();
        try{
            /**
             * 调用SETNX操作获取锁，如果返回true ，代表获取锁成功
             * 代表当前的共享资源还没有被其他线程占据
             * */
            Boolean aBoolean = stringStringValueOperations.setIfAbsent(key, value);
            if (aBoolean){
                this.info("获取到分布式锁");
                /**
                 * 为了防止出现死锁状态，加上EXPIRE操作，即key的过期时间，即20s
                 * */
                stringRedisTemplate.expire(key,20L,TimeUnit.SECONDS);
                this.info("执行业务逻辑");
            }
            endLog(msgValue,startTime);
            return ResponseBo.ok();
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
            return ResponseBo.error("保存失败" + e.getMessage());
        }finally {
            /**
             * 注:访问共享资源结束后要释放锁
             * */
            if (value.equals(stringStringValueOperations.get(key).toString())){
                stringRedisTemplate.delete(key);
            }
        }
    }
    /**
     * @Valid 列表
     * 场景例子:
     *  将一组已经排序好的用户对象存储在缓存中，安排排序好的先后顺序打印到控制台上
     * */
    @ApiOperation(value = "redis 列表(队列)案例演示",notes = "案例:将一组已经排序好的用户对象存储在缓存中，安排排序好的先后顺序打印到控制台上")
    @ResponseBody
    @PostMapping("/list")
    public ResponseBo list(@RequestBody @Valid RedisListVo redisListVo) throws JsonProcessingException {
        String msgValue = "list";
        long startTime = init(msgValue,objectMapper.writeValueAsString(redisListVo));
        try{
            ListOperations listOperations = redisTemplate.opsForList();
            for (RedisVauleModel redisVauleModel:redisListVo.getList()){
                //忘列表中添加数据>>>>>>>>>>从队尾中添加
                listOperations.leftPush(redisListVo.getKey(),redisVauleModel);
            }
            //设置过期时间
            redisTemplate.expire(redisListVo.getKey(),5L, TimeUnit.MINUTES);
            //===============================================================================
            //从队头中遍历，直到没有元素为止
            Object o = listOperations.rightPop(redisListVo.getKey());
            while (null != o){
                info(String.format("当前数据为{%s}",objectMapper.writeValueAsString(o)));
                o = listOperations.rightPop(redisListVo.getKey());
            }
            endLog(msgValue,startTime);
            return ResponseBo.ok();
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
            return ResponseBo.error("保存失败" + e.getMessage());
        }
    }

    /**
     * @Valid 无序集合
     * 场景例子:
     *  给定一组用户列表，要求剔除具有相同姓名的人员并组成新的集合，放至缓存中，打印到控制台
     * */
    @ApiOperation(value = "redis 无序集合Set(队列)案例演示",notes = "案例:给定一组用户列表，要求剔除具有相同姓名的人员并组成新的集合，放至缓存中，打印到控制台")
    @ResponseBody
    @PostMapping("/setList")
    public ResponseBo setList(@RequestBody @Valid RedisSetVo redisSetVo) throws JsonProcessingException {
        String msgValue = "set";
        long startTime = init(msgValue,objectMapper.writeValueAsString(redisSetVo));
        try{
            SetOperations setOperations = redisTemplate.opsForSet();
            for (String value:redisSetVo.getList()){
                setOperations.add(redisSetVo.getKey(),value);
            }
            //设置过期时间
            redisTemplate.expire(redisSetVo.getKey(),5L, TimeUnit.MINUTES);
            //===============================================================================
            //从缓存中获取对象
            Object o = setOperations.pop(redisSetVo.getKey());
            while (null != o){
                info(String.format("当前数据为{%s}",objectMapper.writeValueAsString(o)));
                o = setOperations.pop(redisSetVo.getKey());
            }
            endLog(msgValue,startTime);
            return ResponseBo.ok();
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
            return ResponseBo.error("保存失败" + e.getMessage());
        }
    }

    /**
     *  有序集合
     * 场景例子:
     *  找出一个星期内手机话费单次充值前3名的用户列表，要求充值金额从大到小排序，存至缓存中，
     *  然后在从缓存中取出，打印到控制台证明结果
     * */
    @ApiOperation(value = "redis 有序集合Set(队列)案例演示",notes = "找出一个星期内手机话费单次充值前3名的用户列表，要求充值金额从大到小排序，存至缓存中，然后在从缓存中取出，打印到控制台证明结果")
    @ResponseBody
    @PostMapping("/sortedSet")
    public ResponseBo sortedSet(@RequestBody @Valid RedisSortedSetVo redisSortedSetVo) throws JsonProcessingException {
        String msgValue = "sortedSet";
        long startTime = init(msgValue,objectMapper.writeValueAsString(redisSortedSetVo));
        try{
            ZSetOperations zSetOperations = redisTemplate.opsForZSet();
            //将元素添加到有序集合
            for(PhoneUser phoneUser:redisSortedSetVo.getList()){
                //redis (key值，对象，排序金额)
                zSetOperations.add(redisSortedSetVo.getKey(),phoneUser,phoneUser.getFare());
            }
            //从小到大排序 redis(key值，0，排序截止数值)
            //Set<PhoneUser> set = zSetOperations.range(redisSortedSetVo.getKey(),0L,redisSortedSetVo.getList().size());
            //从大到小排序 redis(key值，0，排序截止数值)
            Set<Object> set = zSetOperations.reverseRange(redisSortedSetVo.getKey(),0L,redisSortedSetVo.getList().size());
            //设置过期时间
            redisTemplate.expire(redisSortedSetVo.getKey(),5L, TimeUnit.MINUTES);
            //===============================================================================
            int i=0;
            for (Object user:set){
                info(String.format("当前数据为{%s}",objectMapper.writeValueAsString(user)));
                i++;
                if (3==i){
                    break;
                }
            }
            endLog(msgValue,startTime);
            return ResponseBo.ok();
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
            return ResponseBo.error("保存失败" + e.getMessage());
        }
    }

    /**
     * @Valid 哈希集合
     * 场景例子:
     *  给定两组数据，一个是老师集合，一个是学生集合，存进缓存中，然后根据所传的老师(学生)索引值，来查找之前存进的数据
     * */
    @ApiOperation(value = "redis 哈希集合案例演示",notes = "案例:给定两组数据，一个是老师集合，一个是学生集合，存进缓存中，然后根据所传的老师(学生)索引值，来查找之前存进的数据")
    @ResponseBody
    @PostMapping("/hashTest")
    public ResponseBo hashTest(@RequestBody @Valid RedisHashVo redisHashVo) throws JsonProcessingException {
        String msgValue = "hashTest";
        //老师 Redis key值
         String tKey="teacher_hash_001";
        //学生 redis key值
        String sKey="student_hash_001";
        //返回根据所传的老师(学生)索引值查询的数据
        Map<String,Object> map = new HashMap<>();
        long startTime = init(msgValue,objectMapper.writeValueAsString(redisHashVo));
        try{
           //将两组数据存入缓存
            HashOperations hashOperations = redisTemplate.opsForHash();
            //老师集合
            for (Teacher teacher:redisHashVo.gettList()){
                hashOperations.put(tKey,teacher.gettId(),teacher);
            }
            //学生集合
            for (Student student:redisHashVo.getsList()){
                hashOperations.put(sKey,student.getsId(),student);
            }
            //设置过期时间
            redisTemplate.expire(tKey,5L, TimeUnit.MINUTES);
            redisTemplate.expire(sKey,5L, TimeUnit.MINUTES);
            //===============================================================================
           //根据所传的老师(学生)索引值查询数据
            map.put("teacher",hashOperations.get(tKey,redisHashVo.gettIndex()));
            map.put("student",hashOperations.get(sKey,redisHashVo.getsIndex()));
            endLog(msgValue,startTime);
            return ResponseBo.ok(map);
        }catch (Exception e){
            endLogError(msgValue,startTime,e);
            return ResponseBo.error("保存失败" + e.getMessage());
        }
    }

    /**
     * @Valid 校验传参值
     * */
    @ApiOperation(value = "存值",notes = "将数据保存到redis")
    @ResponseBody
    @PostMapping("/set")
    public ResponseBo set(@RequestBody @Valid RedisModel redisModel) {
        try{
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            System.out.println(redisModel.toString());
            valueOperations.set(redisModel.getKey(),"{" +
                    "\"vaulebsc\":\""+redisModel.getVaule().getVaulebsc()+"\",\"id\":\""+redisModel.getVaule().getId()+"\"}");
            redisTemplate.expire(redisModel.getKey(),5L, TimeUnit.MINUTES);

            return ResponseBo.ok();
        }catch (Exception e){
            return ResponseBo.error("保存失败" + e.getMessage());
        }
    }
    /**
     * @Valid 校验传参值
     * */
    @ApiOperation(value = "redis队列存值",notes = "将数据保存到redis队列")
    @ResponseBody
    @PostMapping("/setLsit")
    public ResponseBo setLsit(@RequestBody @Valid RedisModel redisModel) {
        try{
            ListOperations<String, String> ListOperations = redisTemplate.opsForList();
            ListOperations.leftPush(redisModel.getKey(),"{" +
                    "\"vaulebsc\":\""+redisModel.getVaule().getVaulebsc()+"\",\"id\":\""+redisModel.getVaule().getId()+"\"}");
            return ResponseBo.ok();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBo.error("保存失败" + e.getMessage());
        }
    }

    @ApiOperation(value = "获取redis队列总数",notes = "获取redis队列总数")
    @GetMapping("/sekectDlCount/{key}")
    public ResponseBo sekectDlCount(@PathVariable  String key){
        Long size = Long.valueOf(0);
        try {
            ListOperations<String, String> ListOperations = redisTemplate.opsForList();
            size = ListOperations.size(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseBo.ok(size);
    }


    @ApiOperation(value = "redis队列取值",notes = "获取redis队列中的数据")
    @GetMapping("/getLsit/{key}")
    public RedisVauleModel getLsit(@PathVariable  String key) {
        RedisVauleModel redisVauleModel = new RedisVauleModel();
        try{
            ListOperations<String, String> ListOperations = redisTemplate.opsForList();
            redisVauleModel = JSON.parseObject(ListOperations.rightPop(key), new TypeReference<RedisVauleModel>(){});
        }catch (Exception e){
            e.printStackTrace();
        }
        return redisVauleModel;
    }

    @ApiOperation(value = "redis  Hash存值",notes = "将数据保存到redis hash")
    @ResponseBody
    @PostMapping("/setHash")
    public ResponseBo setHash(@RequestBody RedisHashModel redisHashModel) {
        try{
            Map<String,String> map = new HashMap<>();
            map.put(redisHashModel.getKey(),redisHashModel.getValue());
            stringRedisTemplate.opsForHash().putAll(redisHashModel.getName(),map);
            return ResponseBo.ok();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBo.error("保存失败" + e.getMessage());
        }
    }

    @ApiOperation(value = "获取 hash redis 精准查询")
    @ResponseBody
    @CrossOrigin(origins = "*")
    @PostMapping("/selectHashVaule")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Hash_key",value = "redis hash key + '___' redis key",required = false,dataType = "String"),
    })
    public ResponseBo selectHashVaule( @RequestBody String Hash_key){
        Object o =null;
        try{
            o = stringRedisTemplate.opsForHash().get(Hash_key.split("___")[0], Hash_key.split("___")[1]);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseBo.error("查询失败");
        }
        return ResponseBo.ok(o);
    }

    @ApiOperation(value = "redis  删除Hash",notes = "删除redis hash")
    @GetMapping("/deleteHash/{key}")
    public ResponseBo deleteHash(@PathVariable  String key) {
        try{
            stringRedisTemplate.delete(key);
            return ResponseBo.ok();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBo.error("保存失败" + e.getMessage());
        }
    }




    @ApiOperation(value = "redis获取hash",notes = "获取redis中的hash")
    @GetMapping("/getHash/{key}")
    public ResponseBo getHash(@PathVariable  String key) {
        try{
            Map values = stringRedisTemplate.opsForHash().entries(key);

            System.out.println(values.toString());
            return ResponseBo.ok(values.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    @ApiOperation(value = "取值",notes = "获取redis中的数据")
    @GetMapping("/get/{key}")
    public RedisVauleModel get(@PathVariable  String key) {
        RedisVauleModel redisVauleModel = new RedisVauleModel();
        try{
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            redisVauleModel = JSON.parseObject(valueOperations.get(key), new TypeReference<RedisVauleModel>(){});
        }catch (Exception e){
            e.printStackTrace();
        }
        return redisVauleModel;
    }


}
