package com.spursgdp.springboot.springboot06jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class SpringBoot06JdbcApplicationTests {

    @Autowired
    private DataSource datasource;

    @Test
    void contextLoads() throws SQLException {

        System.out.println(datasource.getClass());  // class com.zaxxer.hikari.HikariDataSource
                                                    // class com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper

        Connection connection = datasource.getConnection();
        System.out.println(connection);
        connection.close();

    }

}
