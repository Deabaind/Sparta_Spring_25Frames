package com.example.twentyfiveframes.domain.movie.cache;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Profile("local")
public class MemCounter implements KeywordCounter {

    private final Map<String, AtomicInteger> keywordCount = new ConcurrentHashMap<>();

    // 키워드 기록, 키워드를 소문자로 변환 후 카운트 1 증가
    @Override
    public void record(String keyword) {
        if (keyword == null || keyword.isBlank()) return;
        keywordCount
                .computeIfAbsent(keyword.toLowerCase(), k -> new AtomicInteger(0))
                .incrementAndGet();
    }

    // 상위 N개 인기 키워드 조회, 카운트 기준 내림차순 정렬 후 제한된 개수 반환
    @Override
    public List<String> topKeywords(int limit) {
        return keywordCount.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue().get(), a.getValue().get()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }
}