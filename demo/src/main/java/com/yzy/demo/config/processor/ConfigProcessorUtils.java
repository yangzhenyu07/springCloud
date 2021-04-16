package com.yzy.demo.config.processor;


import com.yzy.demo.exception.ExceptionEnum;
import com.yzy.demo.exception.throwtype.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.Assert;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 获取配置文件中的属性值
 */
public class ConfigProcessorUtils {

    private static Logger log = LoggerFactory.getLogger(ConfigProcessorUtils.class);


    private static final  Properties properties = new Properties();

    public static void setValue (String key, String value) {
        if (Singleton.getInstance(null,"NO").getEnvironment().getPropertySources().get("monster-config-properties") == null) {
            log.info("开始获取配置文件中的属性值");
            File file = null;
            FileWriter writer = null;
            FileSystemResource resource = null;
            InputStream inputStream = null;
            try {
                file = File.createTempFile("monster-config", ".properties");//创建临时文件
                writer = new FileWriter(file);
                writer.write("monster=true");
                writer.flush();

                resource = new FileSystemResource(file);
                inputStream = resource.getInputStream();
                properties.load(inputStream);
                // 添加一个默认的空的为后期修改做准备
                List<PropertySource<?>> sources = new PropertiesPropertySourceLoader().load("monster-config-properties", resource);
                for (PropertySource<?> source : sources) {
                    Singleton.getInstance(null,"NO").getEnvironment().getPropertySources().addFirst(source);
                }
            } catch (IOException e) {
                throw new MyException(ExceptionEnum.SYSCONFIG);
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {}
                }
                if (file != null) {
                    file.deleteOnExit();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        log.error("流关闭报错",e);
                        throw new MyException(ExceptionEnum.SYSCONFIG);
                    }
                }
            }
        }
        properties.setProperty(key, value);
        Singleton.getInstance(null,"NO").getEnvironment().getPropertySources().replace("monster-config-properties", new PropertiesPropertySource("monster-config-properties", properties));
    }

    /**
     *   获取单个key
     * @param key
     * @return
     */
    public static String  getPropertiesBySingleKey(String key){
        Assert.hasLength(key,"key不能为空");
        return Singleton.getInstance(null,"NO").getEnvironment().getProperty(key);
    }

    /**
     *  获取属性值
     * @param keys
     * @return
     */
    public static Map<String,String> getProperties(String... keys){
        Assert.notEmpty(keys,"key不能为空");
        Map<String,String> map = new HashMap<>(keys.length *4/3 +1);
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i],Singleton.getInstance(null,"NO").getEnvironment().getProperty(keys[i]));
        }
        return map;
    }




}