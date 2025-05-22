package com.example.twentyfiveframes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AuthRedisConfig {

    @Bean
    public RedisConnectionFactory authRedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6380);
        config.setDatabase(1);
        return new LettuceConnectionFactory(config);
    }

    @Bean(name = "authRedisTemplate")
    public RedisTemplate<String, String> authRedisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(authRedisConnectionFactory());
        return template;
    }
}
