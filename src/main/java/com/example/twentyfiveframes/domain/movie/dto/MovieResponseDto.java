package com.example.twentyfiveframes.domain.movie.dto;

import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.review.dto.ReviewWithLikeDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        private final Double averageRating;

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

    @Getter
    @RequiredArgsConstructor
    public static class Get {
        private final Long movieId;
        private final String title;
        private final String summary;
        private final String director;
        private final Integer ageLimit;
        private final String genre;
        private final Integer runningTime;
        private final LocalDate releaseDate;
        private final Double averageRating;
        private final Long totalViews;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private final LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private final LocalDateTime updatedAt;

        private final List<ReviewWithLikeDto> reviews;

        public static Get from(Movie movie, Long total, List<ReviewWithLikeDto> reviews) {
            return new Get(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getSummary(),
                    movie.getDirector(),
                    movie.getAgeLimit(),
                    movie.getGenre().toString(),
                    movie.getRunningTime(),
                    movie.getReleaseDate(),
                    movie.getAverageRating(),
                    total,
                    movie.getCreatedAt(),
                    movie.getUpdatedAt(),
                    reviews
            );
        }
    }
}
