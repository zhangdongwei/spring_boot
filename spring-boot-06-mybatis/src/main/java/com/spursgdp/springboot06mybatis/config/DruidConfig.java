package com.spursgdp.springboot06mybatis.config;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.sql.SQLUtils;
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

    /**
     * 注意这里不可以配置成spring.datasource.druid，因为application.properties中配置的是spring.datasource.xxx
     */
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean
    public DataSource druid() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setProxyFilters(Arrays.asList(slf4jLogFilter()));  //手动添加
        return druidDataSource;
    }

    /**
     * 1.配置Druid的后台管理的Servlet，如果在application.properties中配置了stat-view-servlet这里就不需要了；
     */
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

    /**
     * 2.配置一个web监控的Filter，如果在application.properties中配置了web-stat-filter这里就不需要了；
     */
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


    /**
     * 配置文件中配置statement-executable-sql-log-enable未生效，在这里配置。
     * 相应的配置项参考LogFilter类。
     * @return
     */
    public Slf4jLogFilter slf4jLogFilter() {
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        slf4jLogFilter.setConnectionLogEnabled(false);
        slf4jLogFilter.setResultSetLogEnabled(false);
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
        slf4jLogFilter.setStatementSqlPrettyFormat(false);
        slf4jLogFilter.setStatementSqlFormatOption(new SQLUtils.FormatOption(true, true));
        return slf4jLogFilter;
    }


}
