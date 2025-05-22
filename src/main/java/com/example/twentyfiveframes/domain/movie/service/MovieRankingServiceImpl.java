package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieRankingResponse;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieRankingServiceImpl implements MovieRankingService {

    private final MovieRepository movieRepository;

    @Override
    @Cacheable(value = "movieRanking")
    public List<MovieRankingResponse> findRanking() {
        return movieRepository.findAll().stream()
                .sorted(Comparator.comparing(Movie::getAverageRating).reversed())
                .limit(10)
                .map(MovieRankingResponse::from)
                .toList();
    }

    // 캐시 무효화 메서드 (리뷰 등록 시 호출될 예정)
    @Override
    @CacheEvict(value = "movieRanking", allEntries = true)
    public void evictRankingCache() {
    }
}