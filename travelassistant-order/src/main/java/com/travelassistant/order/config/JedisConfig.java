package com.travelassistant.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("18")
    private int maxIdle;

    @Value("-1")
    private long maxWaitMillis;

    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        //  根据需要添加其他配置，如最大连接数、最小空闲连接数等

        if (password != null && !password.isEmpty()) {
            return new JedisPool(jedisPoolConfig, host, port, timeout, password);
        } else {
            return new JedisPool(jedisPoolConfig, host, port, timeout);
        }
    }
}
