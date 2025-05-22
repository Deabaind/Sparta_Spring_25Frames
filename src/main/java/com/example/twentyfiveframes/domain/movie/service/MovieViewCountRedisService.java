package com.example.twentyfiveframes.domain.movie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieViewCountRedisService {

    private final RedisTemplate<String, Long> viewCountRedisTemplate;
    private static final String PREFIX = "movie:viewCount:";

    // 해당 Key의 값을 1 증가시킴
    public void increaseViewCount(Long movieId) {
        String key = PREFIX + movieId;
        viewCountRedisTemplate.opsForValue().increment(key, 1L);
    }

    // movieId에 해당하는 오늘의 조회수
    public Long getViewCount(Long movieId) {
        String key = PREFIX + movieId;

        return viewCountRedisTemplate.opsForValue().get(key);
    }


}
