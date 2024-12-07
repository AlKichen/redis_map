package com.task.test_task.controller;

import com.task.test_task.redis.RedisMap;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.commands.JedisCommands;

import java.util.Map;

/**
 * A controller for handling test operations related to Redis data storage and retrieval.
 * This controller provides endpoints to save data to an in-memory Redis-backed map and
 * check for data directly in Redis using a Redis client.
 */
@Controller
@AllArgsConstructor
@Profile({"dev", "prod"})
@RequestMapping("/test")
public class TestController {

    /**
     * A Redis-backed map implementation that acts like a standard Java {@link Map}.
     * This map stores data in Redis while providing the standard {@link Map} interface for usage.
     */
    private final Map<String, Integer> map;

    /**
     * The Redis client.
     */
    private final JedisCommands jedis;

    /**
     * Saves a key-value pair to the Redis-backed map.
     * The key-value pair is stored in Redis, and the operation is similar to storing data in an in-memory map.
     *
     * @param key   The key to be stored in the Redis map.
     * @param value The value to be stored in the Redis map.
     * @return A confirmation message indicating the key and value were saved.
     */
    @PostMapping("/save")
    @ResponseBody
    public String saveToRedisMap(@RequestParam @Nonnull String key, @RequestParam @Nonnull Integer value) {
        this.map.put(key, value);
        return "Saved key: " + key + " with value: " + value + " in RedisMap";
    }

    /**
     * Checks if a given key exists in Redis directly.
     * If the key exists, retrieves its value from Redis and returns a message with the key and value.
     * If the key does not exist, returns a message indicating that the key was not found.
     *
     * @param key The key to check for existence in Redis.
     * @return A message indicating whether the key exists and its value if found.
     */
    @GetMapping("/check-direct")
    @ResponseBody
    public String checkInRedisDirectly(@RequestParam String key) {
        RedisMap redisMap = (RedisMap) this.map;
        boolean exists = jedis.hexists(redisMap.getRedisHashKey(), key);
        if (exists) {
            String value = jedis.hget(redisMap.getRedisHashKey(), key);
            return "Key: " + key + " exists in Redis with value: " + value;
        } else {
            return "Key: " + key + " does not exist in Redis";
        }
    }
}
