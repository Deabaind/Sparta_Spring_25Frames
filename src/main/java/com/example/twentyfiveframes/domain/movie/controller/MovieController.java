package com.example.twentyfiveframes.domain.movie.controller;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.movie.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    // 영화 등록
    @PostMapping
    public ResponseEntity<MovieResponseDto.Save> saveMovie(@AuthenticationPrincipal Long userId,
                                                           @Valid @RequestBody MovieRequestDto.Save dto) {
        MovieResponseDto.Save response = movieService.saveMovie(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 영화 전체 조회
    @GetMapping
    public ResponseEntity<Page<MovieResponseDto.GetAll>> getAllMovies(
            @PageableDefault(sort = "releaseDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MovieResponseDto.GetAll> response = movieService.getAllMovies(pageable);
        return ResponseEntity.ok(response);
    }

    // 영화 단건 조회
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieResponseDto.Get> getMovie(@PathVariable Long movieId) {
        MovieResponseDto.Get response = movieService.getMovie(movieId);
        return ResponseEntity.ok(response);
    }

    // 영화 수정
    @PatchMapping("/{movieId}")
    public ResponseEntity<String> updateMovie(@AuthenticationPrincipal Long userId,
                                              @PathVariable Long movieId,
                                              @Valid @RequestBody MovieRequestDto.Update dto) {
        movieService.updateMovie(userId, movieId, dto);
        return ResponseEntity.ok("영화 정보가 수정되었습니다.");
    }

    // 영화 삭제
    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteMovie(@AuthenticationPrincipal Long userId,
                                              @PathVariable Long movieId) {
        movieService.deleteMovie(userId, movieId);
        return ResponseEntity.ok("등록된 영화가 삭제되었습니다.");
    }

    // 키워드 기반 영화 검색, 제목과 장르 키워드를 이용해 영화 리스트 반환
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre) {
        List<Movie> result = movieService.search(title, genre);
        return ResponseEntity.ok(result);
    }

    // 인기 검색어 조회, 최근 인기 검색어 상위 N개 반환
    @GetMapping("/popular")
    public ResponseEntity<List<String>> topKeywords(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(movieService.topKeywords(limit));
    }
}