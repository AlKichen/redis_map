package com.task.test_task.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.*;
import redis.clients.jedis.commands.JedisCommands;

import java.util.HashSet;
import java.util.Set;

@Configuration
@AllArgsConstructor
public class RedisConfig {

    private RedisProperties redisProperties;

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

        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder()
                .user(userName.isEmpty() ? null : userName)
                .password(password.isEmpty() ? null : password)
                .timeoutMillis(connectionTimeOut)
                .build();

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
                throw new UnsupportedOperationException("Unsupported mode: " + redisProperties.getMode().name());
            }
            case REDIS_CLUSTER -> {
                return new JedisCluster(nodes, clientConfig);
            }
            default -> throw new IllegalArgumentException("Unknown Redis mode: " + redisProperties.getMode());
        }
    }
}