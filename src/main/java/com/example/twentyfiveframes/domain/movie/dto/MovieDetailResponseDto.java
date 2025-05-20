package com.example.twentyfiveframes.domain.movie.dto;

import com.example.twentyfiveframes.domain.movie.entity.MovieGenre;
import com.example.twentyfiveframes.domain.review.dto.ReviewWithLikeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MovieDetailResponseDto {
    private Long movieId;
    private String title;
    private MovieGenre genre;
    private String director;
    private List<ReviewWithLikeDto> reviews;
}
