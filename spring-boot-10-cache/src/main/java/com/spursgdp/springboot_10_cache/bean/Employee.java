package com.spursgdp.springboot_10_cache.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhangdongwei
 * @create 2020-03-01-10:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    private Integer id;

    private String lastName;

    private String email;

    private Integer gender; //性别 1男 0女

    private Integer dId;   //外键：departmentId

//    public Employee(Integer id, String lastName, String email, Integer gender, Integer dId) {
//        this.id = id;
//        this.lastName = lastName;
//        this.email = email;
//        this.gender = gender;
//        this.dId = dId;
//    }
}
