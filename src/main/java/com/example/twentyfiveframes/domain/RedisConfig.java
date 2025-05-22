package com.example.twentyfiveframes.domain;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6380);
        config.setDatabase(0);
        return new LettuceConnectionFactory(config); // application.yml 설정 필요
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

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

    @Bean
    public RedisConnectionFactory viewCountConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6380);
        config.setDatabase(2);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Long> viewCountRedisTemplate() {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(viewCountConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        return template;
    }
}
