package com.task.test_task.redis;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import redis.clients.jedis.commands.JedisCommands;

import java.util.*;

@AllArgsConstructor
@Component
public class RedisMap implements Map<String, Integer> {

    private final JedisCommands jedis;

    private final String redisHashKey = "RedisMap_" + UUID.randomUUID();

    @Override
    public int size() {
        return Math.toIntExact(jedis.hlen(redisHashKey));
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return jedis.hexists(redisHashKey, key.toString());
    }

    @Override
    public boolean containsValue(Object value) {
        List<String> values = jedis.hvals(redisHashKey);
        return values.contains(value.toString());
    }

    @Override
    public Integer get(Object key) {
        String value = jedis.hget(redisHashKey, key.toString());
        return value != null ? Integer.valueOf(value) : null;
    }

    @Override
    public Integer put(String key, Integer value) {
        jedis.hset(redisHashKey, key, value.toString());
        return value;
    }

    @Override
    public Integer remove(Object key) {
        long result = jedis.hdel(redisHashKey, key.toString());
        return result == 1 ? 1 : 0;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Integer> m) {
        Map<String, String> redisMap = new HashMap<>();
        for (Entry<? extends String, ? extends Integer> entry : m.entrySet()) {
            redisMap.put(entry.getKey(), entry.getValue().toString());
        }
        jedis.hmset(redisHashKey, redisMap);
    }

    @Override
    public void clear() {
        jedis.del(redisHashKey);
    }

    @Override
    public Set<String> keySet() {
        return jedis.hkeys(redisHashKey);
    }

    @Override
    public Collection<Integer> values() {
        List<String> values = jedis.hvals(redisHashKey);
        List<Integer> intValues = new ArrayList<>();
        for (String value : values) {
            intValues.add(Integer.valueOf(value));
        }
        return intValues;
    }

    @Override
    public Set<Entry<String, Integer>> entrySet() {
        Map<String, String> redisMap = jedis.hgetAll(redisHashKey);
        Set<Entry<String, Integer>> entrySet = new HashSet<>();
        for (Entry<String, String> entry : redisMap.entrySet()) {
            entrySet.add(new AbstractMap.SimpleEntry<>(entry.getKey(), Integer.valueOf(entry.getValue())));
        }
        return entrySet;
    }

    public String getRedisHashKey() {
        return this.redisHashKey;
    }
}