package com.spursgdp.springboot_10_cache.redis;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisTests {

    @Autowired
    RedisTemplate redisTemplate;   //k-v都是对象的

    @Autowired
    StringRedisTemplate stringRedisTemplate;  //k-v都是String的

    /**
     * Redis常见的五大数据类型：
     *   String(字符串）：stringRedisTemplate.opsForValue()
     *   List(列表）：stringRedisTemplate.opsForList()
     *   Set(集合）：stringRedisTemplate.opsForSet()
     *   Hash(散列）：stringRedisTemplate.opsForHash()
     *   ZSet(有序集合)：stringRedisTemplate.opsForZSet()
     */

    @Test
    public void test01() {
        System.out.println("begin...");

        //1 string相关操作
        //set & get
        stringRedisTemplate.opsForValue().set("hello", "world");  //OK
        String value = stringRedisTemplate.opsForValue().get("hello");  //world
        System.out.println(value);
        //incr
        stringRedisTemplate.opsForValue().set("counter", "5");
        Long counter = stringRedisTemplate.opsForValue().increment("counter"); //6
        System.out.println(counter);
        //expire & ttl
        stringRedisTemplate.expire("hello", 5, TimeUnit.SECONDS);
        //mset & mget
        HashMap<String, String> map = new HashMap<String, String>() {
            {
                put("a", "1");
                put("b", "2");
                put("c", "3");
                put("d", "4");
            }
        };
        stringRedisTemplate.opsForValue().multiSetIfAbsent(map);

        //2.list相关操作
        stringRedisTemplate.delete("mylist");
        //rpush
        stringRedisTemplate.opsForList().rightPush("mylist", "1");
        stringRedisTemplate.opsForList().rightPush("mylist", "2");
        stringRedisTemplate.opsForList().rightPush("mylist", "3");
        //llen
        Long len = stringRedisTemplate.opsForList().size("mylist");
        System.out.println(len);
        //lrange
        List<String> values = stringRedisTemplate.opsForList().range("mylist", 0, -1); //start=0,end=-1，代表查询所有数据，结果为[1,2,3]
        System.out.println(values);

        //3.set相关操作
        stringRedisTemplate.delete("myset");
        //sadd
        stringRedisTemplate.opsForSet().add("myset", "a", "b", "c");
        //smembers
        Set<String> setValues = stringRedisTemplate.opsForSet().members("myset");
        System.out.println(setValues);   //[a,b]

        //4.sorted set相关操作
        String k = "user:rank";
        stringRedisTemplate.delete(k);
        //zadd
        stringRedisTemplate.opsForZSet().add(k, "martin", 250);
        stringRedisTemplate.opsForZSet().add(k, "frank", 200);
        stringRedisTemplate.opsForZSet().add(k, "mike", 91);
        stringRedisTemplate.opsForZSet().add(k, "tim", 220);
        stringRedisTemplate.opsForZSet().add(k, "kris", 1);
        //zcard
        len = stringRedisTemplate.opsForZSet().zCard(k);
        System.out.println(len);
        //zrangeWithScores
        Set<ZSetOperations.TypedTuple<String>> tuples = stringRedisTemplate.opsForZSet().rangeWithScores(k, 0, -1);
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            System.out.println(tuple.getValue() + ":" + tuple.getScore());
        }

        //5.hash相关操作
        k = "user:1";
        stringRedisTemplate.delete(k);
        //hmset
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("name", "mike");
        map1.put("age", "12");
        map1.put("city", "tianjin");
        map1.put("sex", "man");
        stringRedisTemplate.opsForHash().putAll(k, map1);
        //hmget
        List<Object> hmgetValues = stringRedisTemplate.opsForHash().multiGet(k, Lists.list("name", "sex"));
        System.out.println(hmgetValues);
        //hexists
        Boolean flag = stringRedisTemplate.opsForHash().hasKey(k, "city");
        System.out.println(flag);   //true

        //6.key相关操作
        //keys
        Set<String> keys = stringRedisTemplate.keys("*");
        System.out.println(keys);
        keys = stringRedisTemplate.keys("user*");
        System.out.println(keys);
        //exists
        flag = stringRedisTemplate.hasKey("user:rank");
        System.out.println(flag);

        System.out.println("end...");
    }

}
