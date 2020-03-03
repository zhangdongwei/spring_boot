package com.spursgdp.springboot.springboot06jdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zhangdongwei
 * @create 2020-03-03-9:27
 */
@RestController
public class JdbcController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @GetMapping("deps")
    public List<Map<String,Object>> getAllDepartments() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from department");
        return maps;
    }

}
