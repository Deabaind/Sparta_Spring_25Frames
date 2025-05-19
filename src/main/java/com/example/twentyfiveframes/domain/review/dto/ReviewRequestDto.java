package com.example.twentyfiveframes.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

//리뷰 등록/수정 요청용 DTO
@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    private Long movieId;
    private int rating;
    private String content;

}
