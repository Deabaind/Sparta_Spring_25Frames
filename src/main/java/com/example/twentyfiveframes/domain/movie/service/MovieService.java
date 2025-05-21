package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Movie getMovieById(Long movieId);

    MovieResponseDto.Save saveMovie(Long userId, MovieRequestDto.Save dto);

    Page<MovieResponseDto.GetAll> getAllMovies(Pageable pageable);

    MovieResponseDto.Get getMovie(Long movieId);

    void updateMovie(Long userId, Long movieId, MovieRequestDto.Update dto);

    void deleteMove(Long movieId);
}
