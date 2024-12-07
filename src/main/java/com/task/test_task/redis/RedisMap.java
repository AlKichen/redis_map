package com.task.test_task.redis;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import redis.clients.jedis.commands.JedisCommands;

import java.util.*;

/**
 * A Redis-backed implementation of the {@link Map} interface, providing an in-memory
 * map-like structure that interacts with Redis to store, retrieve, and manage data.
 * This class implements all the standard methods from the {@link Map} interface,
 * allowing it to be used like a regular Java map while persisting data in Redis.
 *
 * <p>Example usage of this class can be found in the {@link com.task.test_task.controller.TestController}.</p>
 */
@AllArgsConstructor
@Component
public class RedisMap implements Map<String, Integer> {

    /**
     * The Redis client used to perform Redis operations.
     */
    private final JedisCommands jedis;

    /**
     * A unique Redis hash key generated for this instance, used to identify the map in Redis.
     */
    private final String redisHashKey = "RedisMap_" + UUID.randomUUID();

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return The number of key-value mappings in the map.
     */
    @Override
    public int size() {
        return Math.toIntExact(jedis.hlen(redisHashKey));
    }

    /**
     * Checks if the map is empty.
     *
     * @return {@code true} if the map contains no key-value mappings, otherwise {@code false}.
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Checks if the map contains a mapping for the specified key.
     *
     * @param key The key whose presence in the map is to be tested.
     * @return {@code true} if the map contains a mapping for the specified key, otherwise {@code false}.
     */
    @Override
    public boolean containsKey(Object key) {
        return jedis.hexists(redisHashKey, key.toString());
    }

    /**
     * Checks if the map contains one or more mappings for the specified value.
     *
     * @param value The value whose presence in the map is to be tested.
     * @return {@code true} if the map contains one or more mappings for the specified value, otherwise {@code false}.
     */
    @Override
    public boolean containsValue(Object value) {
        List<String> values = jedis.hvals(redisHashKey);
        return values.contains(value.toString());
    }

    /**
     * Retrieves the value to which the specified key is mapped, or {@code null} if this map
     * contains no mapping for the key.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value to which the specified key is mapped, or {@code null} if no mapping exists.
     */
    @Override
    public Integer get(Object key) {
        String value = jedis.hget(redisHashKey, key.toString());
        return value != null ? Integer.valueOf(value) : null;
    }

    /**
     * Associates the specified value with the specified key in the map. If the map previously
     * contained a mapping for the key, the old value is replaced.
     *
     * @param key   The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @return The previous value associated with the key.
     */
    @Override
    public Integer put(@Nonnull String key, @Nonnull Integer value) {
        if (key == null || value == null) {
            throw new NullPointerException(
                    "This realization of Map (RedisMap) couldn't contain null key or null values."
            );
        }
        jedis.hset(redisHashKey, key, value.toString());
        return value;
    }

    /**
     * Removes the mapping for a key from the map if it is present.
     *
     * @param key The key whose mapping is to be removed.
     * @return The previous value associated with the key, or {@code null} if the key was not found.
     */
    @Override
    public Integer remove(Object key) {
        Integer value = this.get(key);
        long result = jedis.hdel(redisHashKey, key.toString());
        return result == 1 ? value : null;
    }

    /**
     * Copies all of the mappings from the specified map to this map. The {@code putAll} operation
     * replaces any existing mappings for the keys with the new mappings.
     *
     * @param m The mappings to be stored in this map.
     */
    @Override
    public void putAll(Map<? extends String, ? extends Integer> m) {
        Map<String, String> redisMap = new HashMap<>();
        for (Entry<? extends String, ? extends Integer> entry : m.entrySet()) {
            redisMap.put(entry.getKey(), entry.getValue().toString());
        }
        jedis.hmset(redisHashKey, redisMap);
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        jedis.del(redisHashKey);
    }

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     *
     * @return A set view of the keys contained in this map.
     */
    @Override
    public Set<String> keySet() {
        return jedis.hkeys(redisHashKey);
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     *
     * @return A collection view of the values contained in this map.
     */
    @Override
    public Collection<Integer> values() {
        List<String> values = jedis.hvals(redisHashKey);
        List<Integer> intValues = new ArrayList<>();
        for (String value : values) {
            intValues.add(Integer.valueOf(value));
        }
        return intValues;
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     *
     * @return A set view of the mappings contained in this map.
     */
    @Override
    public Set<Entry<String, Integer>> entrySet() {
        Map<String, String> redisMap = jedis.hgetAll(redisHashKey);
        Set<Entry<String, Integer>> entrySet = new HashSet<>();
        for (Entry<String, String> entry : redisMap.entrySet()) {
            entrySet.add(new AbstractMap.SimpleEntry<>(entry.getKey(), Integer.valueOf(entry.getValue())));
        }
        return entrySet;
    }

    /**
     * Returns the Redis hash key used to identify this map in Redis.
     *
     * @return The unique Redis hash key for this map.
     */
    public String getRedisHashKey() {
        return this.redisHashKey;
    }
}