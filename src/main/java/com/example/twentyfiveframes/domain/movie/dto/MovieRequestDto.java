package com.example.twentyfiveframes.domain.movie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class MovieRequestDto {


    // 영화 등록
    @Getter
    @AllArgsConstructor
    public static class Create {

        @NotBlank(message = "영화 제목은 필수 입력 항목입니다.")
        @Size(max = 30, message = "30자 이하로 입력할 수 있습니다.")
        private String title;

        @NotBlank(message = "영화 줄거리는 필수 입력 항목입니다.")
        private String summary;

        @NotBlank(message = "감독명은 필수 입력 항목입니다.")
        @Size(max = 30, message = "30자 이하로 입력할 수 있습니다.")
        private String director;

        @NotNull(message = "관람 제한 연령은 필수 입력 항목입니다.")
        private Long ageLimit; //0,12,15,18 연령제한

        @NotBlank(message = "영화 장르는 필수 입력 항목입니다.")
        private String genre; //이넘으로 변경

        @NotNull(message = "영화 개봉일은 필수 입력 항목입니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate releaseDate;
    }


    // 영화 수정

    // 영화 전체 조회

    // 영화 단건 조회

    // 영화 삭제

}
