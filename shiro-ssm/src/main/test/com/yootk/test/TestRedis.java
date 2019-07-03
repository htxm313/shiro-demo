package com.yootk.test;

import com.yootk.ssm.util.cache.RedisCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:spring/spring-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedis {
    @Autowired
    private RedisCache<Object,Object> redisCache ;
    @Test
    public void test() {
        this.redisCache.put("hello","HappyLee") ;
    }
    @Test
    public void testGet() {
        System.out.println(this.redisCache.get("hello")) ;
    }
}
