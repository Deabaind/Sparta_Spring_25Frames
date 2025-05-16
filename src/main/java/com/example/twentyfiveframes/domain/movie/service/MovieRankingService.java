package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieRankingResponse;
import java.util.List;

public interface MovieRankingService {
    List<MovieRankingResponse> findRanking();
}