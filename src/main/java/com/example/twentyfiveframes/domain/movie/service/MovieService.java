package com.example.twentyfiveframes.domain.movie.service;

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

    // 검색
    List<Movie> search(String title, String genre);
    List<String> topKeywords(int limit);
}
