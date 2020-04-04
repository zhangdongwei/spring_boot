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
 *
 *  原理：
 *    1、自动配置类；CacheAutoConfiguration
 *          在springboot的autoconfig包中定义了该类（24行）。
 *          通过CacheConfigurationImportSelector导入所有的缓存配置类CacheConfiguration（171行）
 *    2、缓存的配置类xxxCacheConfiguration（11个，按顺序加载）
 *          org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
 *          org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
 *          org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
 *          org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
 *          org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
 *          org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
 *          org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
 *          org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
 *          org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
 *          org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration【默认】
 *          org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
 *
 *    3、可见，默认生效的配置类是SimpleCacheConfiguration（内部基于ConcurrentHashMap的单机缓存），
 *       但如果引入了spring-boot-redis-starter，则生效的配置类就是RedisCacheConfiguration
 *    4、SimpleCacheConfiguration中给Spring容器中注册了一个CacheManager（51行）：ConcurrentMapCacheManager
 *    5、ConcurrentMapCacheManager内部通过ConcurrentMapCache实现缓存功能（170行），将数据保存在ConcurrentMap中（store变量，52行）,
 *       每个cacheName对应着ConcurrentMap中的一个key。
 *
 *  运行流程：
 *    @Cacheable：
 *      1、方法运行之前，调用CacheManager.getCache()获取相应的Cache（缓存组件），按照cacheNames指定的名字获取(本例是emp），
 *         第一次获取缓存如果没有Cache组件会自动创建
 *      2、然后调用Cache.get()查找缓存的内容（在ConcurrentMapCache.lookup()中设置断点回溯），使用一个key，默认就是方法的参数；
 *           key是按照某种策略生成的；基于keyGenerator生成，默认使用SimpleKeyGenerator生成key（当@CacheEnable上未设置key和keyGenerator属性）；
 *               SimpleKeyGenerator生成key的默认策略（在SimpleKeyGenerator.generator()中设置断点）；
 *                  如果没有参数；key=new SimpleKey()；
 *                  如果有一个参数：key=参数的值
 *                  如果有多个参数：key=new SimpleKey(params)；
 *      3、没有查到缓存就调用目标方法；
 *      4、将目标方法返回的结果，放进缓存中（在ConcurrentMapCache.put()中设置断点）
 *
 *     总结： @Cacheable标注的方法执行之前先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存，
 *     如果没有就运行方法并将结果放入缓存；以后再来调用就可以直接使用缓存中的数据；
 *
 *   核心：
 *      1）、使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConcurrentMapCache】组件，如没有则创建
 *      2）、key使用keyGenerator生成的，默认是SimpleKeyGenerator
 *
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
