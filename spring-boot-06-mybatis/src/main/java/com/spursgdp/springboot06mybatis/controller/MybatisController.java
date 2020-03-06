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

    @GetMapping("/insertDept")
    public Department insertDepartment(Department dept) {
        log.info("插入Department: " + dept);
        departmentMapper.insertDept(dept);
        return dept;
    }

    @GetMapping("/updDept")
    public Department updateDepartment(Department dept) {
        log.info("修改Department: " + dept);
        departmentMapper.updateDept(dept);
        return dept;
    }

    @GetMapping("/delDept/{id}")
    public String delDeptById(@PathVariable("id") Integer id) {
        log.info("删除Department: " + id);
        departmentMapper.deleteDeptById(id);
        return "success";
    }

}
