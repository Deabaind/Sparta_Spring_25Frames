package com.example.twentyfiveframes.domain.movie.cache;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class KeywordCounter {

    private final Map<String, AtomicInteger> keywordCount = new ConcurrentHashMap<>();

    public void record(String keyword) {
        if (keyword == null || keyword.isBlank()) return;
        keywordCount
                .computeIfAbsent(keyword.toLowerCase(), k -> new AtomicInteger(0))
                .incrementAndGet();
    }

    public List<String> getTopKeywords(int limit) {
        return keywordCount.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().get(), e1.getValue().get()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }
}
