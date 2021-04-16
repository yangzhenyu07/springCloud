package com.yzy.demo.config.processor;


import com.yzy.demo.exception.ExceptionEnum;
import com.yzy.demo.exception.throwtype.MyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义环境加载策略
 * 需要在classpath下application.yml或application.properties中指定文件位置, key=monster.config.file.path
 *
 */
public class ConfigEnvironmentPostProcessor implements EnvironmentPostProcessor {
    /**
     * ,
     * 指定加载外部文件类型: [properties], [yml], [yaml]
     */
    private static final String SUFFIX_TYPE_YML = "yml";
    private static final String SUFFIX_TYPE_YAML = "yaml";
    private static final String SUFFIX_TYPE_PROPERTIES = "properties";

    /**
     * 指定外部配置文件路径的KEY
     */
    private static final String CONFIG_FILE_PATH = "config.configLoad";

    /**
     * 读取配置文件住方法
     * @param environment 配置文件加载对象
     * @param application spring
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        PropertySourceLoader loader = null;
        String pathArr = environment.getProperty(CONFIG_FILE_PATH);
        if (StringUtils.isNotEmpty(pathArr)) {
            String[] splitPath = pathArr.split(",");
            try {
                String contentPath = System.getProperty("user.dir");
                for (int i = 0; i < splitPath.length; i++) {
                    if (StringUtils.isEmpty(splitPath[i])) {
                        continue;
                    }
                    // 加载外部配置文件
                    loadConfig(environment, loader, "file:" + contentPath + "/config/" + splitPath[i]);
                    // 加载jar内部配置文件
                    loadConfig(environment, loader, splitPath[i]);
                }
            } catch (IOException e) {
                throw new MyException(ExceptionEnum.SYSCONFIG);
            }
        }
    }

    /**
     * 加载外部config的配置文件
     * @param filePath 配置的加载文件的路径
     */
    private void loadConfig (ConfigurableEnvironment environment, PropertySourceLoader loader, String filePath) throws IOException {
        // 获取所有的yml配置文件
        Resource [] resourcesYml = new PathMatchingResourcePatternResolver().getResources(filePath + "/**/*." + SUFFIX_TYPE_YML);
        Resource [] resourcesYaml = new PathMatchingResourcePatternResolver().getResources(filePath + "/**/*." + SUFFIX_TYPE_YAML);
        int ymlLength = resourcesYml.length,yamlLength = resourcesYaml.length;
        resourcesYml = Arrays.copyOf(resourcesYml, ymlLength + yamlLength);//数组扩容
        System.arraycopy(resourcesYaml, 0, resourcesYml, ymlLength, yamlLength);
        for (Resource resource : resourcesYml) {
            loader = new YamlPropertySourceLoader();
            List<PropertySource<?>> sources = loader.load(resource.getURI().toString(), resource);
            for (PropertySource<?> source : sources) {
                environment.getPropertySources().addLast(source);
            }
        }
        Resource [] resourcesProperties = new PathMatchingResourcePatternResolver().getResources(filePath + "/**/*." + SUFFIX_TYPE_PROPERTIES);
        for (Resource resource : resourcesProperties) {
            loader = new PropertiesPropertySourceLoader();
            List<PropertySource<?>> sources = loader.load(resource.getURI().toString(), resource);
            for (PropertySource<?> source : sources) {
                environment.getPropertySources().addLast(source);
            }
        }
    }
}