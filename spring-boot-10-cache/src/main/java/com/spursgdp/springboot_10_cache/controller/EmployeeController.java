package com.spursgdp.springboot_10_cache.controller;

import com.spursgdp.springboot_10_cache.bean.Employee;
import com.spursgdp.springboot_10_cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangdongwei
 * @create 2020-03-01-11:18
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee select(@PathVariable("id") Integer id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping("/emp")
    public Employee update(Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @GetMapping("/delemp")
    public String delete(Integer id) {
        employeeService.deleteEmployee(id);
        return "success";
    }

    @GetMapping("/emp/lastname/{lastName}")
    public Employee getEmpByLastName(@PathVariable("lastName") String lastName) {
        return employeeService.getEmployeeByLastname(lastName);
    }

}
