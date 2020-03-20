package com.spursgdp.springboot02config.config;

import com.spursgdp.springboot02config.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangdongwei
 * @create 2020-02-14-17:15
 */
@Configuration
public class MyAppConfig {

    @Bean(value = "helloService")
    public HelloService helloService(){
        System.out.println("配置类给容器中添加组件了...");
        return new HelloService();
    }
}
