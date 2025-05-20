package com.example.twentyfiveframes.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewWithLikeDto {
    private Long reviewId;
    private Long userId;
    private String username;
    private int rating;
    private String content;
    private int likeCount;

}
