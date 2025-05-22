package com.example.twentyfiveframes.domain.movie.cache;

import java.util.List;

public interface KeywordCounter {
    void record(String keyword);
    List<String> topKeywords(int limit);
}