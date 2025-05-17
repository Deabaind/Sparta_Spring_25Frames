package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;

public interface MovieService {
    MovieResponseDto.create saveMovie(MovieRequestDto.Create dto);
}
