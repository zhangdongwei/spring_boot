package com.spursgdp.springboot_10_cache.service;

import com.spursgdp.springboot_10_cache.bean.Employee;
import com.spursgdp.springboot_10_cache.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author zhangdongwei
 * @create 2020-03-01-11:15
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "emp")
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * SpringBoot提供的CacheManager可以用来管理多个Cache组件，缓存真正的CRUD操作是在Cache组件中，
     * 每一个缓存组件有自己唯一的一个名字（cacheNames/value）
     * @Cacheable注解的几个属性（可参考Cacheable接口的源码）：
     *      cacheNames/value：指定缓存组件的名字；将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个缓存；
     *      key：指定缓存的key。默认是使用方法参数的值
     *              编写spEL：#id;参数id的值  #a0 #p0 #root.args[0]
     *      keyGenerator：key的生成器。key/keyGenerator只能二选一
     *      cacheManager：指定缓存管理器，或者cacheResolver指定获取解析器
     *      condition：指定符合条件才缓存
     *              condition = "#id>0"
     *      unless：与condition相反，符合条件的不缓存，其余缓存
     *              unless = "#result == null"
     *      sync：是否使用异步模式
     *
     * @param id  Employee id
     * @return
     */
    @Cacheable(/*cacheNames = "emp",*/key = "#id")
    public Employee getEmployee(Integer id) {
        log.info("执行查询：查询id = " + id);
        return employeeMapper.getEmployeeById(id);
    }

    /**
     * @CachePut：既调用方法（如修改DB），又同步更新缓存
     * 运行机制：
     *   （1）先调用目标方法
     *   （2）将目标方法的结果缓存起来
     * @param employee
     */
    @CachePut(/*cacheNames = "emp",*/key = "#employee.id")
    public Employee updateEmployee(Employee employee) {
        log.info("执行修改：" + employee);
        employeeMapper.updateEmployee(employee);
        return employee;
    }

    /**
     * @CacheEvt：缓存清除
     *    key: 指定要清楚的数据
     *    allEntries = true: 指定清除这个缓存中的所有数据
     *    beforeInvocation = false: 缓存的清除是否在方法之前执行
     *         默认的缓存清除操作是在方法执行之后执行；如果出现异常缓存就不会清除
     *
     *    beforInvocation = true:
     *         清楚缓存操作在方法执行之前执行，无论方法是否出现异常，缓存都执行
     */
    @CacheEvict(/*value = "emp",*/key = "#id")
    public void deleteEmployee(Integer id) {
        log.info("执行删除：" + id);
//        employeeMapper.deleteEmployeeById(id);
    }

    @Caching(
            cacheable = {
                    @Cacheable(key = "#lastName")
            },
            put = {
                    @CachePut(key = "#result.id"),
                    @CachePut(key = "#result.email")
            }
    )
    public Employee getEmployeeByLastname(String lastName) {
        log.info("根据lastName查询：" + lastName);
        return employeeMapper.getEmployeeByLastName(lastName);
    }

}
