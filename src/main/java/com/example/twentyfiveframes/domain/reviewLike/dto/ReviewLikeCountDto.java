package com.example.twentyfiveframes.domain.reviewLike.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

//리뷰 ID별 좋아요 수 반환용
@Getter
@AllArgsConstructor
public class ReviewLikeCountDto {

    private Long reviewId;
    private int likeCount;
}
