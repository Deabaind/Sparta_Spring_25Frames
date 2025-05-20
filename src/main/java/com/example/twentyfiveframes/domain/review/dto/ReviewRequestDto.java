package com.example.twentyfiveframes.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//리뷰 등록/수정 요청용 DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {
    private Long movieId;
    private int rating;
    private String content;

}
