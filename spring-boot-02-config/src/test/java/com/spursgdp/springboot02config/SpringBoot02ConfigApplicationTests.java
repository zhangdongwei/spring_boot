package com.spursgdp.springboot02config;

import com.spursgdp.springboot02config.bean.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringBoot02ConfigApplicationTests {

    @Autowired
    private Person person;

    @Autowired
    private ApplicationContext ioc;

    @Test
    void contextLoads() {
//        System.out.println(person);
        System.out.println(ioc.containsBean("helloService"));  //true
    }

}
