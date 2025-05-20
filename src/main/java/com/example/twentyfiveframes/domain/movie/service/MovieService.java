package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    MovieResponseDto.Save saveMovie(User user, MovieRequestDto.Save dto);

    Page<MovieResponseDto.GetAll> getAllMovies(Pageable pageable);

    // 검색
    List<Movie> search(String title, String genre);
    List<String> topKeywords(int limit);
}
