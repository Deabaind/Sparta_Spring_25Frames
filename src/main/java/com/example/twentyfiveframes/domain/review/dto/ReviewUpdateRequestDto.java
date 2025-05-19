package com.example.twentyfiveframes.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {
    private int rating;
    private String content;
}
