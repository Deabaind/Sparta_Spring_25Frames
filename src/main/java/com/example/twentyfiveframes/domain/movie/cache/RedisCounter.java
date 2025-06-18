package com.example.twentyfiveframes.domain.movie.cache;

import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("stringRedisTemplate")
    private final StringRedisTemplate stringRedisTemplate;

    // 생성자 주입으로 RedisTemplate 받아오기
    public RedisCounter(StringRedisTemplate redisTemplate) {
        this.stringRedisTemplate = redisTemplate;
    }

    // 키워드 기록, 소문자로 변환 후 Redis SortedSet 점수 1 증가
    @Override
    public void record(String keyword) {
        if (keyword == null || keyword.isBlank()) return;
        stringRedisTemplate.opsForZSet().incrementScore(KEY, keyword.toLowerCase(), 1);
    }

    // 상위 N개 인기 키워드 조회, 점수 내림차순 정렬 후 결과 반환
    @Override
    public List<String> topKeywords(int limit) {
        Set<String> result = stringRedisTemplate.opsForZSet().reverseRange(KEY, 0, limit - 1);
        return (result == null) ? List.of() : new ArrayList<>(result);
    }
}