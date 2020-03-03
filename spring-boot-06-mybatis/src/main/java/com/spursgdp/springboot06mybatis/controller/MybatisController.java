package com.spursgdp.springboot06mybatis.controller;

import com.spursgdp.springboot06mybatis.bean.Department;
import com.spursgdp.springboot06mybatis.mapper.DepartmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangdongwei
 * @create 2020-03-03-15:18
 */
@Slf4j
@RestController
public class MybatisController {

    @Autowired
    private DepartmentMapper departmentMapper;

    @GetMapping("/dept/{id}")
    public Department getDeptById(@PathVariable("id") Integer id) {
        log.info("查询Department: " + id);
        return departmentMapper.getDeptById(id);
    }

    @GetMapping("/dept")
    public Department insertDepartment(Department dept) {
        log.info("插入Department: " + dept);
        departmentMapper.insertDept(dept);
        return dept;
    }

}
