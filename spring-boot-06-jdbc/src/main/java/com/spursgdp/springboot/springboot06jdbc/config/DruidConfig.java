package com.spursgdp.springboot.springboot06jdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author zhangdongwei
 * @create 2020-03-03-11:19
 */
@Configuration
public class DruidConfig {

    //注意这里不可以配置成spring.datasource.druid，因为application.properties中配置的是spring.datasource.xxx
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }

    //配置Druid的监控
    //1.配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        HashMap<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername","druid");
        initParams.put("loginPassword","druid");
        initParams.put("allow",""); //默认允许所有访问
        initParams.put("deny","192.168.15.21");

        servletRegistrationBean.setInitParameters(initParams);

        return servletRegistrationBean;
    }

    //2.配置一个web监控的Filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        filterRegistrationBean.setFilter(new WebStatFilter());

        HashMap<String, String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/");   //对静态资源不金控统计

        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));

        return filterRegistrationBean;
    }

}
