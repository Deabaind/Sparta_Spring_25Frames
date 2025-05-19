package com.example.twentyfiveframes.domain.reviewLike.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


//좋아요 정보 응답용 DTO
@Getter
@AllArgsConstructor
public class ReviewLikeResponseDto {

    private Long reviewId;
    private Long userId;
    private LocalDateTime likedAt;
}
