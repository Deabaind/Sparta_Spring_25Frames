package com.example.twentyfiveframes.domain.movie.dto;

import com.example.twentyfiveframes.domain.movie.entity.Movie;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class MovieResponseDto {

    @Getter
    @RequiredArgsConstructor
    public static class Save {
        private final String message;
        private final Long movieId;
    }

    @Getter
    @RequiredArgsConstructor
    public static class GetAll {
        private final Long movieId;
        private final String title;
        private final String summary;
        private final String genre;
        private final Double averageScore;

        public static GetAll from(Movie movie) {
            Double averageRating = movie.getAverageRating();
            Double rounded = null;

            if(averageRating != null) {
                rounded = Math.round(averageRating * 10) / 10.0;
            }

            return new GetAll(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getSummary(),
                    movie.getGenre().toString(),
                    rounded
            );
        }
    }
}
