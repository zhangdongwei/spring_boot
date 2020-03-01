package com.spursgdp.springboot_10_cache;

import com.spursgdp.springboot_10_cache.bean.Employee;
import com.spursgdp.springboot_10_cache.mapper.EmployeeMapper;
import com.spursgdp.springboot_10_cache.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBoot10CacheApplicationTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    void contextLoads() {
//        Employee e1 = employeeMapper.getEmployeeById(1);
//        System.out.println(e1);
        Employee e1 = employeeService.getEmployee(1);
        System.out.println(e1);

    }

}
