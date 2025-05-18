package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.user.entity.User;

public interface MovieService {
    MovieResponseDto.create saveMovie(User user, MovieRequestDto.Create dto);
}
