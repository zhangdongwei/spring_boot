package com.spursgdp.springboot06mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangdongwei
 * @create 2020-03-03-15:48
 */
@Configuration
@MapperScan(basePackages = "com.spursgdp.springboot06mybatis.mapper")
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        ConfigurationCustomizer configurationCustomizer = new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                //可以进行MyBatis的相关配置
                configuration.setMapUnderscoreToCamelCase(true);  //支持驼峰命名法
            }
        };
        return configurationCustomizer;
    }

}
