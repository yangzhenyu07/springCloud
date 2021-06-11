package com.yzy.demo.javatest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

class R{
    private String a;
    private long b;
    private Timestamp c;
    private Date d;

    public void setA(String a) {
        this.a = a;
    }

    public void setB(long b) {
        this.b = b;
    }

    public void setC(Timestamp c) {
        this.c = c;
    }

    public void setD(Date d) {
        this.d = d;
    }

    public String getA() {
        return a;
    }

    public long getB() {
        return b;
    }

    public Timestamp getC() {
        return c;
    }

    public Date getD() {
        return d;
    }
}
public class Test1 {
    private static final String msg = "{\"test\":{\n" +
            " \"a\":\"15\",\n" +
            " \"b\":\"89\",\n" +
            " \"c\":\"2021-06-07 12:12:12\",\n" +
            " \"d\":\"2021-05-23 12:12:12\"\n" +
            "}}";

    public static void main(String[] args) {
        //json 转 Map
        Map<String,Object> map = JSONObject.parseObject(msg);
        System.out.println("String 转Map:"+map);
        //Map 转 String
        String jsonString = JSON.toJSONString(map.get("test"));
        System.out.println("传参:"+jsonString);
        // json 转 jsonObject
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        R r = new R();
        //jsonObject 转各种类型
        r.setA(jsonObject.getString("a"));
        r.setB(jsonObject.getLongValue("b"));
        r.setC(jsonObject.getTimestamp("c"));
        r.setD(jsonObject.getSqlDate("d"));
        //String 转对象
        R r2 = JSON.parseObject(jsonString,new TypeReference<R>(){
        });
        //Object 转 String
        String r1 = JSON.toJSONString(r);
        System.out.println("R对象转成String输出:"+r1);
    }
}
