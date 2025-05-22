package com.example.twentyfiveframes.domain.movie.cache;

import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Primary
public class RedisCounter implements KeywordCounter {

    private static final String KEY = "search:keyword";
    private final StringRedisTemplate redisTemplate;

    public RedisCounter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void record(String keyword) {
        if (keyword == null || keyword.isBlank()) return;
        redisTemplate.opsForZSet().incrementScore(KEY, keyword.toLowerCase(), 1);
    }

    @Override
    public List<String> topKeywords(int limit) {
        Set<String> result = redisTemplate.opsForZSet().reverseRange(KEY, 0, limit - 1);
        return (result == null) ? List.of() : new ArrayList<>(result);
    }
}