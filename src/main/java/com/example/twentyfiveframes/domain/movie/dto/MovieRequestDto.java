package com.example.twentyfiveframes.domain.movie.dto;

import com.example.twentyfiveframes.domain.movie.entity.MovieGenre;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class MovieRequestDto {


    // 영화 등록
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    public static class Save {

        @NotBlank(message = "영화 제목은 필수 입력 항목입니다.")
        @Size(max = 30, message = "30자 이하로 입력할 수 있습니다.")
        private String title;

        @NotBlank(message = "영화 줄거리는 필수 입력 항목입니다.")
        private String summary;

        @NotBlank(message = "감독명은 필수 입력 항목입니다.")
        @Size(max = 30, message = "30자 이하로 입력할 수 있습니다.")
        private String director;

        @NotNull(message = "관람 제한 연령은 필수 입력 항목입니다.")
        private Integer ageLimit; //0,12,15,18 연령제한

        @NotNull(message = "영화 장르는 필수 입력 항목입니다.")
        private MovieGenre genre;

        @NotNull(message = "관람시간은 필수 입력 항목입니다.")
        private Integer runningTime;

        @NotNull(message = "영화 개봉일은 필수 입력 항목입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate releaseDate;
    }


    // 영화 수정
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {

        @Size(max = 30, message = "30자 이하로 입력할 수 있습니다.")
        private String title;

        private String summary;

        @Size(max = 30, message = "30자 이하로 입력할 수 있습니다.")
        private String director;

        private Integer ageLimit; //0,12,15,18 연령제한

        private MovieGenre genre;

        private Integer runningTime;

    }


}
