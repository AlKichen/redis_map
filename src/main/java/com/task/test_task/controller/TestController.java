package com.task.test_task.controller;

import com.task.test_task.redis.RedisMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.commands.JedisCommands;

@Controller
@AllArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final RedisMap redisMap;

    private final JedisCommands jedis;

    @PostMapping("/save")
    @ResponseBody
    public String saveToRedisMap(@RequestParam String key, @RequestParam Integer value) {
        redisMap.put(key, value);
        return "Saved key: " + key + " with value: " + value + " in RedisMap";
    }

    @GetMapping("/check-direct")
    @ResponseBody
    public String checkInRedisDirectly(@RequestParam String key) {
        boolean exists = jedis.hexists(redisMap.getRedisHashKey(), key);
        if (exists) {
            String value = jedis.hget(redisMap.getRedisHashKey(), key);
            return "Key: " + key + " exists in Redis with value: " + value;
        } else {
            return "Key: " + key + " does not exist in Redis";
        }
    }
}
