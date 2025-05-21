package com.example.twentyfiveframes.domain.review.controller;

import com.example.twentyfiveframes.domain.review.dto.MessageResponseDto;
import com.example.twentyfiveframes.domain.review.dto.ReviewRequestDto;
import com.example.twentyfiveframes.domain.review.dto.ReviewUpdateRequestDto;
import com.example.twentyfiveframes.domain.review.service.ReviewService;
import com.example.twentyfiveframes.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 1. 리뷰 등록
    @PostMapping
    public ResponseEntity<Void> createReview(@RequestBody ReviewRequestDto requestDto,
                                             @AuthenticationPrincipal Long userId) {
        reviewService.createReview(userId, requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 2. 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<MessageResponseDto> updateReview(@PathVariable("reviewId") Long reviewId,
                                                           @RequestBody ReviewUpdateRequestDto dto,
                                                           @AuthenticationPrincipal Long userId) {
        reviewService.updateReview(reviewId, dto, userId);
        return ResponseEntity.ok(new MessageResponseDto("리뷰가 수정되었습니다."));
    }

    // 3. 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long reviewId,
                                             @AuthenticationPrincipal Long userId) {
        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // 5. 리뷰 좋아요 등록
    @PostMapping("/{reviewId}/like")
    public ResponseEntity<MessageResponseDto> likeReview(@PathVariable("reviewId") Long reviewId,
                                                         @AuthenticationPrincipal Long userId) {
        reviewService.likeReview(reviewId, userId);
        return ResponseEntity.ok(new MessageResponseDto("리뷰에 좋아요를 눌렀습니다."));
    }

}
