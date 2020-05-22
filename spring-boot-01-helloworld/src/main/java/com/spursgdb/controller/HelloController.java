package com.spursgdb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangdongwei
 * @create 2020-02-12-10:42
 */

@RestController // @RestController = @RestBody + @Controller
//@CrossOrigin(origins = "http://localhost:8081")
public class HelloController {

    @RequestMapping("/hello")
//    @CrossOrigin(origins = "http://localhost:8081")
    public String hello() {
        return "Hello cors!";
    }

}
