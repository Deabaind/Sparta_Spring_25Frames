package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ViewCountScheduler {

    private final MovieRepository movieRepository;
    @Qualifier("viewCountRedisTemplate")
    private final RedisTemplate<String, Long> redisTemplate;

    // redis에서 DB로 저장
    @Scheduled(cron = "0 0 0 * * *")
    public void updateTotalViewsFromRedis() {

        Set<String> keys = redisTemplate.keys("movie:views:*");

        if (keys == null || keys.isEmpty()) {return;}

        for (String key : keys) {
            Long movieId = Long.valueOf(key.replace("movie:views:",""));
            Long addCount = redisTemplate.opsForValue().get(key);

            if (addCount != null) {
                movieRepository.findById(movieId).ifPresent(movie -> {
                    movie.addViews(addCount);
                });
            }

        }

    }

}
