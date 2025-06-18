package com.example.twentyfiveframes.domain.movie.dto;

import com.example.twentyfiveframes.domain.movie.entity.MovieGenre;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KeywordSearchResponseDto {

    private final Long movieId;

    private final String title;

    private final MovieGenre genre;

    private final Double averageScore;
}