package com.example.demo.controller;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RestController
public class TestController {
    private StatefulRedisConnection<String, String> connection;
    @PostConstruct
    private void init(){
        RedisURI redisUri = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        RedisClient redisClient = RedisClient.create(redisUri);
        connection = redisClient.connect();
    }
    @RequestMapping("/zadd")
    public void zadd(){
        connection.sync().zadd("test-key",123D,123L);
    }

    @RequestMapping("/get")
    public void get(){
        connection.sync().get("test-key");
    }

}
