package com.task.test_task.config;

import com.task.test_task.config.RedisProperties;
import lombok.AllArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.clients.jedis.*;
import redis.clients.jedis.commands.JedisCommands;

import java.util.HashSet;
import java.util.Set;

/**
 * Configuration class for setting up a Redis client.
 * This class provides a Spring Bean that configures a Redis connection based on
 * the provided properties.
 */
@Configuration
@Profile("test")
@AllArgsConstructor
public class RedisTestConfig {

    /**
     * The properties used to configure the Redis connection.
     */
    private RedisProperties redisProperties;

    /**
     * Configures and returns a Jedis client instance based on the provided Redis properties.
     * This method creates and configures a Redis client instance based on the mode specified in the
     * provided {@link RedisProperties}. The client instance is returned to be used as a Spring Bean
     * for the application context, enabling seamless interaction with the Redis database.
     *
     * <p>Depending on the mode specified in the Redis properties, the method will create and return:
     * <ul>
     *   <li><strong>Stand-Alone Mode:</strong> A standalone Redis client connected to a single Redis node.</li>
     *   <li><strong>Sentinel Mode:</strong> A Redis client configured for high availability using Sentinel,
     *       which will handle automatic failover and monitoring of Redis nodes.</li>
     *   <li><strong>Redis Cluster Mode:</strong> A Redis client configured for clustered Redis setups,
     *       supporting data sharding and distributed storage.</li>
     * </ul>
     *
     * <p>Each mode is configured using the provided properties, such as host, port, username, password,
     * and connection timeout. For modes requiring authentication, the client will be configured to use
     * the provided credentials.
     *
     * <p>After the client is created, it is returned to the application context as a Spring Bean for
     * further use, allowing the application to communicate with the Redis database as needed.
     *
     * @return A configured Jedis client instance.
     * @throws IllegalArgumentException If an unknown Redis mode is provided or the configuration
     *         cannot be matched to a supported Redis mode.
     */
    @Bean
    public JedisCommands jedis() {
        Set<HostAndPort> nodes = new HashSet<>();
        for (String node : redisProperties.getNodes()) {
            String[] hostPort = node.split(":");
            nodes.add(new HostAndPort(hostPort[0], Integer.parseInt(hostPort[1])));
        }

        String userName = redisProperties.getUserName();
        String password = redisProperties.getPassword();
        int connectionTimeOut = redisProperties.getConnectionTimeOut();

        JedisClientConfig clientConfig = this.buildJedisClientConfig(userName, password, connectionTimeOut);

        switch (redisProperties.getMode()) {
            case STAND_ALONE -> {
                String[] hostPort = redisProperties.getNodes()[0].split(":");
                Jedis jedis = new Jedis(hostPort[0], Integer.parseInt(hostPort[1]), connectionTimeOut);
                if (!userName.isEmpty() || !password.isEmpty()) {
                    jedis.auth(password.isEmpty() ? "" : password);
                }
                return jedis;
            }
            case SENTINEL -> {
                JedisSentinelPool sentinelPool = new JedisSentinelPool(
                        this.redisProperties.getMasterNameForSentinel(),
                        nodes,
                        new GenericObjectPoolConfig<>(),
                        clientConfig,
                        DefaultJedisClientConfig.builder().build()
                );
                return sentinelPool.getResource();
            }
            case REDIS_CLUSTER -> {
                return new JedisCluster(nodes, clientConfig);
            }
            default -> throw new IllegalArgumentException("Unknown Redis mode: " + redisProperties.getMode());
        }
    }

    /**
     * Builds a JedisClientConfig with the provided parameters.
     *
     * @param userName The username for authentication, if any.
     * @param password The password for authentication, if any.
     * @param connectionTimeOut The connection timeout in milliseconds.
     * @return A configured JedisClientConfig instance.
     */
    private JedisClientConfig buildJedisClientConfig(String userName, String password, int connectionTimeOut) {
        return DefaultJedisClientConfig.builder()
                .user(userName.isEmpty() ? null : userName)
                .password(password.isEmpty() ? null : password)
                .timeoutMillis(connectionTimeOut)
                .build();
    }
}