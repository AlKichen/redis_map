package com.task.test_task.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "redis")
@Validated
@Getter
public class RedisProperties {

    private final RedisMode mode;

    private final String userName;

    private final String password;

    private final String[] nodes;

    private final int connectionTimeOut;

    public RedisProperties(RedisMode mode, String userName, String password, String[] nodes, int connectionTimeOut) {
        this.mode = mode;
        this.userName = userName;
        this.password = password;
        this.nodes = nodes;
        this.connectionTimeOut = connectionTimeOut;
    }
}