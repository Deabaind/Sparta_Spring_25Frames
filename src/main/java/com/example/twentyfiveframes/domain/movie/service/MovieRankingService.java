package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieRankingResponse;
import java.util.List;

public interface MovieRankingService {
    List<MovieRankingResponse> findRanking();
    // 캐시 삭제용 메서드 (리뷰 기능 연동용)
    void evictRankingCache();
}