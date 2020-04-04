package com.spursgdp.springboot06mybatis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class SpringBoot06MybatisApplicationTests {

    @Autowired
    ApplicationContext ioc;

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());  // class com.zaxxer.hikari.HikariDataSource
        // class com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper


        try(Connection connection = dataSource.getConnection()){
            System.out.println(connection);
        }
    }

}
