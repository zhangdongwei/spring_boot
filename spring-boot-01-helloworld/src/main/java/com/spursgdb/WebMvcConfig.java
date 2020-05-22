package com.spursgdb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhangdongwei
 * @create 2020-05-22-13:06
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")              //所有controller
                .allowedOrigins("http://localhost:8081")  //允许跨域的请求域名
                .allowedHeaders("*")                      //允许所有请求头通过
                .allowedMethods("*")                      //允许所有的方法（GET、POST）通过
                .maxAge(30*1000);                         //探测请求的生效时间
    }
}
