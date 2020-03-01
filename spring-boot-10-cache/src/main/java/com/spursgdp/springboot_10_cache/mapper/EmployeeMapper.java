package com.spursgdp.springboot_10_cache.mapper;

import com.spursgdp.springboot_10_cache.bean.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangdongwei
 * @create 2020-03-01-10:50
 * Mapper类：为了简化，模拟操作DB
 */
@Component
public class EmployeeMapper {

    private static List<Employee> employeeList = new ArrayList<>();

    static {
        Employee e1 = new Employee(2,"lisi","lisi@qq.com",1,1);
        Employee e2 = new Employee(1,"zhangsan","zhangsan@gmail.com",1,1);
        employeeList.addAll(Arrays.asList(e1,e2));
    }

    //查询
    public Employee getEmployeeById(Integer id) {
        return employeeList.stream().filter(employee -> employee.getId() == id).findFirst().get();
    }

    //新增
    public void insertEmployee(Employee employee) {
        employeeList.add(employee);
    }

    //删除
    public void deleteEmployeeById(Integer id) {
        employeeList = employeeList.stream().filter(employee -> employee.getId() != id).collect(Collectors.toList());
    }

    //修改
    public void updateEmployee(Employee employee) {
        employeeList = employeeList.stream().map(e -> e.getId() == employee.getId() ? employee : e).collect(Collectors.toList());
    }

    //根据lastName查询
    public Employee getEmployeeByLastName(String lastName) {
        return employeeList.stream().filter(employee -> employee.getLastName().equals(lastName)).findFirst().get();
    }

}
