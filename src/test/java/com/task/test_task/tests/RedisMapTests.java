package com.task.test_task.tests;

import com.task.test_task.config.RedisMode;
import com.task.test_task.redis.RedisMap;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.commands.JedisCommands;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link RedisMap} implementation.
 * <p>
 * These tests validate the behavior of {@code RedisMap} methods using an in-memory Redis instance.
 * The {@link RedisMap} is set up before each test to ensure isolated execution.
 * And cleared after each test except {@link RedisMode#REDIS_CLUSTER}, for this mode,
 * you have to clear data from DB manually.
 *
 * </p>
 */
@SpringBootTest
class RedisMapTests {

    /**
     * Redis commands interface used for interaction with the Redis instance.
     */
    @Autowired
    private JedisCommands jedis;

    /**
     * Instance of {@link RedisMap} to be tested.
     */
    private RedisMap redisMap;

    /**
     * Sets up a new {@link RedisMap} instance before each test.
     */
    @BeforeEach
    void setUp() {
        redisMap = new RedisMap(jedis);
    }

    /**
     * Cleans up the Redis database after each test.
     * <p>
     * Uses {@code FLUSHDB} for clearing data. Logs a warning if the operation is not supported.
     * </p>
     * <b>Cleared after each test realized, except {@link RedisMode#REDIS_CLUSTER}, for this mode,
     * you have to clear data from DB manually.</b>
     */
    @AfterEach
    void tearDown() {
        if (jedis instanceof Jedis) {
            ((Jedis) jedis).flushDB();
        } else {
            System.err.println(
                    "FLUSHDB not supported for this JedisCommands implementation."
                            + " Please clear data from DB manually"
            );
        }
    }

    /**
     * Tests the {@link RedisMap#size()} method.
     */
    @Test
    void testSize() {
        redisMap.put("key1", 1);
        redisMap.put("key2", 2);
        assertEquals(2, redisMap.size());
    }

    /**
     * Tests the {@link RedisMap#isEmpty()} method.
     */
    @Test
    void testIsEmpty() {
        assertTrue(redisMap.isEmpty());
        redisMap.put("key1", 1);
        assertFalse(redisMap.isEmpty());
    }

    /**
     * Tests the {@link RedisMap#containsKey(Object)} method.
     */
    @Test
    void testContainsKey() {
        redisMap.put("key1", 1);
        assertTrue(redisMap.containsKey("key1"));
        assertFalse(redisMap.containsKey("key2"));
    }

    /**
     * Tests the {@link RedisMap#containsValue(Object)} method.
     */
    @Test
    void testContainsValue() {
        redisMap.put("key1", 1);
        assertTrue(redisMap.containsValue(1));
        assertFalse(redisMap.containsValue(2));
    }

    /**
     * Tests the {@link RedisMap#get(Object)} method.
     */
    @Test
    void testGet() {
        redisMap.put("key1", 1);
        assertEquals(1, redisMap.get("key1"));
        assertNull(redisMap.get("key2"));
    }

    /**
     * Tests the {@link RedisMap#put(String, Integer))} method.
     */
    @Test
    void testPut() {
        redisMap.put("key1", 1);
        redisMap.put("key2", 2);
        assertEquals(2, redisMap.size());
        assertEquals(1, redisMap.get("key1"));
    }

    /**
     * Tests the {@link RedisMap#remove(Object)} method.
     */
    @Test
    void testRemove() {
        redisMap.put("key1", 1);
        redisMap.put("key2", 2);
        redisMap.remove("key1");
        assertFalse(redisMap.containsKey("key1"));
        assertTrue(redisMap.containsKey("key2"));
    }

    /**
     * Tests the {@link RedisMap#putAll(Map)} method.
     */
    @Test
    void testPutAll() {
        Map<String, Integer> data = Map.of("key1", 1, "key2", 2, "key3", 3);
        redisMap.putAll(data);
        assertEquals(3, redisMap.size());
    }

    /**
     * Tests the {@link RedisMap#clear()} method.
     */
    @Test
    void testClear() {
        redisMap.put("key1", 1);
        redisMap.put("key2", 2);
        redisMap.clear();
        assertTrue(redisMap.isEmpty());
    }

    /**
     * Tests the {@link RedisMap#keySet()} method.
     */
    @Test
    void testKeySet() {
        redisMap.put("key1", 1);
        redisMap.put("key2", 2);
        assertTrue(redisMap.keySet().containsAll(Map.of("key1", 1, "key2", 2).keySet()));
    }

    /**
     * Tests the {@link RedisMap#values()} method.
     */
    @Test
    void testValues() {
        redisMap.put("key1", 1);
        redisMap.put("key2", 2);
        assertTrue(redisMap.values().containsAll(Map.of("key1", 1, "key2", 2).values()));
    }

    /**
     * Tests the {@link RedisMap#entrySet()} method.
     */
    @Test
    void testEntrySet() {
        redisMap.put("key1", 1);
        redisMap.put("key2", 2);
        assertEquals(2, redisMap.entrySet().size());
    }

    /**
     * Tests the {@link RedisMap#getRedisHashKey()} method.
     */
    @Test
    void testGetRedisHashKey() {
        assertNotNull(redisMap.getRedisHashKey());
    }
}
