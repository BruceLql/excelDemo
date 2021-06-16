package com.bruce.demo.controller;

import com.bruce.demo.bean.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author 李启岚(起冉)
 */
@Slf4j
@RestController
@Api(value = "Excel 相关")
@RequestMapping("api/test")
@RequiredArgsConstructor
public class TestController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/set")
    public String set() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setAge(22);
        user.setSex(1);
        valueOperations.set("hello", user);
        Object hello = valueOperations.get("hello");
        log.info("======================:{}", hello);
        return hello.toString();
    }

    @PostMapping("/get")
    public String get() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object hello = valueOperations.get("hello");
        log.info("======================:{}", hello);
        return hello.toString();
    }
}
