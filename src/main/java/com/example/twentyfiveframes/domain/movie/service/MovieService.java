package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.dto.KeywordSearchResponseDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    Movie getMovieById(Long movieId);

    MovieResponseDto.Save saveMovie(Long userId, MovieRequestDto.Save dto);

    Page<MovieResponseDto.GetAll> getAllMovies(Pageable pageable);

    MovieResponseDto.Get getMovie(Long movieId);

    // 영화 수정
    void updateMovie(Long userId, Long movieId, MovieRequestDto.Update dto);

    void deleteMovie(Long userId, Long movieId);

    // 키워드 기반 영화 검색
    List<KeywordSearchResponseDto> search(String title, String genre);

    // 인기 검색어 상위 N개 조회
    List<String> topKeywords(int limit);
}