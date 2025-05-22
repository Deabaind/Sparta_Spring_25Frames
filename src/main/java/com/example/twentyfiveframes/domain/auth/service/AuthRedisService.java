package com.example.twentyfiveframes.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthRedisService {

    @Qualifier("authRedisTemplate")
    private final RedisTemplate<String, String> redisTemplate;

    private static final String PREFIX = "refresh:user:";

    public void save(String userId, String refreshToken, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(PREFIX + userId, refreshToken, timeout, unit);
    }

    public void update(String userId, String refreshToken, long timeout, TimeUnit unit) {
        redisTemplate.delete(PREFIX + userId);
        redisTemplate.opsForValue().set(PREFIX + userId, refreshToken, timeout, unit);
    }

    public void delete(String userId) {
        redisTemplate.delete(PREFIX + userId);
    }

    public boolean valid(String userId, String token) {
        String storedToken = redisTemplate.opsForValue().get(PREFIX + userId);
        return storedToken != null && storedToken.equals(token);
    }
}
