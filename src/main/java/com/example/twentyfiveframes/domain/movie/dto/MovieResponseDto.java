package com.example.twentyfiveframes.domain.movie.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class MovieResponseDto {

    @Getter
    @RequiredArgsConstructor
    public static class Save {
        private final String message;
        private final Long movieId;
    }
}
