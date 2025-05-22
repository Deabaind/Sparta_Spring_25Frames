package com.example.twentyfiveframes.domain.movie.dto;

import com.example.twentyfiveframes.domain.movie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class MovieRankingResponse implements Serializable{
    private Long movieId;
    private String title;
    private Double averageRating;

    public static MovieRankingResponse from(Movie movie) {
        return new MovieRankingResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getAverageRating()
        );
    }
}