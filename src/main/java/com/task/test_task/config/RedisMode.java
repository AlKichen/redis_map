package com.task.test_task.config;

/**
 * Enum representing different Redis modes.
 */
public enum RedisMode {

    /**
     * Standalone mode for Redis.
     */
    STAND_ALONE("standalone"),

    /**
     * Sentinel mode for Redis.
     */
    SENTINEL("sentinel"),

    /**
     * Redis Cluster mode.
     */
    REDIS_CLUSTER("redis-cluster");

    private final String mode;

    RedisMode(String mode) {
        this.mode = mode;
    }

    /**
     * Returns the string representation of the Redis mode.
     *
     * @return the mode as a string
     */
    public String getMode() {
        return mode;
    }
}