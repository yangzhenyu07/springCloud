package com.yzy.demo.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
/**
 * http post
 * */
public class HttpClientHelper {

    public static String httpPostJson(String urlParam, String json, String type,String charset, String authorization) {
        HttpClient client = null;
        HttpPost httpPost = null;
        HttpResponse response = null;
        try {
            client = HttpClients.createDefault();
            httpPost = new HttpPost(urlParam);
            if (StringUtils.isNotEmpty(type)){
                httpPost.setHeader("Content-Type", type+"charset=" + charset);
            }else{
                httpPost.setHeader("Content-Type", "application/json;charset=" + charset);
            }
            httpPost.setHeader("authorization", authorization);
            if (null != json){
                StringEntity stringEntity = new StringEntity(json, charset);
                httpPost.setEntity(stringEntity);
            }
            response = client.execute(httpPost);
            HttpEntity en = response.getEntity();
            String result = EntityUtils.toString(en, charset);
            return result;
        } catch (Exception e) {
            return "HttpClient - NO - Exception";
        }
    }
}
