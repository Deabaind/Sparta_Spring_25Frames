package com.example.twentyfiveframes.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthRedisService {

    @Qualifier("refreshRedisTemplate")
    private final RedisTemplate<String, String> refreshRedisTemplate;

    @Qualifier("accessRedisTemplate")
    private final RedisTemplate<String, String> accessRedisTemplate;

    private static final String PREFIX = "refresh:user:";
    private static final String BLACKLIST = "blackList:";

    public void save(String userId, String refreshToken, long timeout, TimeUnit unit) {
        refreshRedisTemplate.opsForValue().set(PREFIX + userId, refreshToken, timeout, unit);
    }

    public void update(String userId, String refreshToken, long timeout, TimeUnit unit) {
        refreshRedisTemplate.delete(PREFIX + userId);
        refreshRedisTemplate.opsForValue().set(PREFIX + userId, refreshToken, timeout, unit);
    }

    public void delete(String userId) {
        refreshRedisTemplate.delete(PREFIX + userId);
    }

    public boolean validRefreshToken(String userId, String token) {
        String storedToken = refreshRedisTemplate.opsForValue().get(PREFIX + userId);
        return storedToken != null && storedToken.equals(token);
    }

    public void blackList(String accessToken, long timeout, TimeUnit unit) {
        accessRedisTemplate.opsForValue().set(BLACKLIST + accessToken, "logout", timeout, unit);
    }

    public boolean validAccessToken(String accessToken) {
        return accessRedisTemplate.hasKey(BLACKLIST + accessToken);
    }
}
