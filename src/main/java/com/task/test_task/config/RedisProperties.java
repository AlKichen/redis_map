package com.task.test_task.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties class for Redis.
 * This class is used to map the properties defined in the application.yml
 * related to Redis configuration.
 */
@ConfigurationProperties(prefix = "redis-configuration")
@Getter
public class RedisProperties {

    /**
     * The mode in which Redis is running.
     *
     * @see RedisMode
     */
    private final RedisMode mode;

    /**
     * The username for connecting to the Redis server.
     */
    private final String userName;

    /**
     * The password for connecting to the Redis server.
     */
    private final String password;

    /**
     * The array of nodes (host and port) for the Redis setup.
     */
    private final String[] nodes;

    /**
     * The connection timeout in seconds for connecting to the Redis server.
     */
    private final int connectionTimeOut;

    /**
     * The name of the master node. Used only when {@link #mode}
     * equals {@link RedisMode#SENTINEL}.
     */
    private final String masterNameForSentinel;

    /**
     * Constructs a new instance of RedisProperties with the specified values.
     *
     * @param mode                The mode in which Redis is running.
     * @param userName            The username for connecting to the Redis server.
     * @param password            The password for connecting to the Redis server.
     * @param nodes               The array of nodes (host and port) for the Redis setup.
     * @param connectionTimeOut   The connection timeout in seconds for connecting to the Redis server.
     * @param masterNameForSentinel The name of the master node for {@link RedisMode#SENTINEL}.
     */
    public RedisProperties(
            RedisMode mode,
            String userName,
            String password,
            String[] nodes,
            int connectionTimeOut,
            String masterNameForSentinel
    ) {
        this.mode = mode;
        this.userName = userName;
        this.password = password;
        this.nodes = nodes;
        this.connectionTimeOut = connectionTimeOut;
        this.masterNameForSentinel = masterNameForSentinel;
    }
}