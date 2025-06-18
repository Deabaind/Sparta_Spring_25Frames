package com.example.twentyfiveframes.domain.movie.controller;

import com.example.twentyfiveframes.domain.movie.dto.MovieRankingResponse;
import com.example.twentyfiveframes.domain.movie.service.MovieRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieRankingController {

    private final MovieRankingService movieRankingService;

    @GetMapping("/ranking")
    public ResponseEntity<List<MovieRankingResponse>> findRanking() {
        List<MovieRankingResponse> response = movieRankingService.findRanking();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 랭킹 캐시 강제 비우기 (테스트용) 0519 : 0810 작성
    @DeleteMapping("/ranking/cache")
    public ResponseEntity<Void> evictRankingCache() {
        movieRankingService.evictRankingCache();
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}