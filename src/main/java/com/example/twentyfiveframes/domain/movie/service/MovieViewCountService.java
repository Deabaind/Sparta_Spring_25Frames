package com.example.twentyfiveframes.domain.movie.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MovieViewCountService {

    private final Map<Long, Long> viewCountCache = new ConcurrentHashMap<>(); //movieId - 오늘 누적 조회수 저장

    // 캐시에 +1
    public void increaseViewCount(Long movieId) {
        viewCountCache.merge(movieId, 1L, (a, b) -> a + b);
    }

    // movieId에 해당하는 오늘의 조회수
    public Long getTodayViews(Long movieId) {
        return viewCountCache.getOrDefault(movieId, 0L);
    }

    // 하루 끝나면 DB 반영할 전체 Map 꺼내기
    public Map<Long, Long> getAllViewCounts() {
        return new HashMap<>(viewCountCache);
    }

    // 배치 후 캐시 초기화
    public void clear() {
        viewCountCache.clear();
    }


}
