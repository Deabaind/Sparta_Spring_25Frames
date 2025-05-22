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

    @Override
    public void record(String keyword) {
        if (keyword == null || keyword.isBlank()) return;
        keywordCount
                .computeIfAbsent(keyword.toLowerCase(), k -> new AtomicInteger(0))
                .incrementAndGet();
    }

    @Override
    public List<String> topKeywords(int limit) {
        return keywordCount.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue().get(), a.getValue().get()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }
}