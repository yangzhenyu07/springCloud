
package com.yzy.demo;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.yzy.demo.config.processor.ConfigProcessorUtils;
import com.yzy.demo.config.processor.Singleton;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 微服务应用服务启动类
 * @author yangzhenyu
 * @EnableCaching 开启缓存
 * @EnableFeignClients 开启feign远程调用
 * @EnableDiscoveryClient 注册中心客户端
 * @EnableTransactionManagement 开启事务
 * @SpringBootApplication 启动配置
 */
@EnableCaching
@EnableFeignClients
@EnableDiscoveryClient
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages={"com.yzy"},exclude = DruidDataSourceAutoConfigure.class)
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
		/**
		 * 获取自定义配置的对象(单例)
		 * */
		Singleton.getInstance(run.getEnvironment(),"YES");

		System.out.printf("=================="+ ConfigProcessorUtils.getPropertiesBySingleKey("xxx.hhh")+"==================");

	}

}
            