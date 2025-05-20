package com.example.twentyfiveframes.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

//리뷰 조회 응답용 DTO
@Getter
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private Long userId;
    private String username;
    private Long movieId;
    private int rating;
    private String content;
}
