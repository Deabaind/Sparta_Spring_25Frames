package com.example.twentyfiveframes.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableCaching
public class RedisConfig {

    // cashe 관리하는 redis
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

    // 유지중인 refresh Token 저장하는 redis
    @Bean
    public RedisConnectionFactory refreshRedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6380);
        config.setDatabase(1);
        return new LettuceConnectionFactory(config);
    }

    @Bean(name = "refreshRedisTemplate")
    public RedisTemplate<String, String> refreshRedisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(refreshRedisConnectionFactory());
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

    // 블랙리스트 access Token 저장하는 redis
    @Bean
    public RedisConnectionFactory accessRedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6380);
        config.setDatabase(3);
        return new LettuceConnectionFactory(config);
    }

    @Bean(name = "accessRedisTemplate")
    public RedisTemplate<String, String> accessRedisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(accessRedisConnectionFactory());
        return template;
    }

    // 키워드 검색에 필요한 redis
    @Bean
    public RedisConnectionFactory keywordRedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6380);
        config.setDatabase(4);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(keywordRedisConnectionFactory());
    }
}