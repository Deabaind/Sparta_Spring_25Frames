package com.example.twentyfiveframes.domain.review.controller;

import com.example.twentyfiveframes.domain.review.dto.ReviewRequestDto;
import com.example.twentyfiveframes.domain.review.dto.ReviewResponseDto;
import com.example.twentyfiveframes.domain.review.dto.ReviewUpdateRequestDto;
import com.example.twentyfiveframes.domain.review.service.ReviewService;
import com.example.twentyfiveframes.domain.reviewLike.dto.ReviewLikeCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 1. 리뷰 등록
    @PostMapping
    public ResponseEntity<Void> createReview(@RequestBody ReviewRequestDto requestDto,
                                             @RequestParam Long userId) {
        reviewService.createReview(userId, requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 2. 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable Long reviewId,
                                             @RequestBody ReviewUpdateRequestDto dto,
                                             @RequestParam Long userId) {
        reviewService.updateReview(reviewId, dto, userId);
        return ResponseEntity.ok().build();
    }

    // 3. 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId,
                                             @RequestParam Long userId) {
        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // 5. 리뷰 좋아요 등록
    @PostMapping("/{reviewId}/like")
    public ResponseEntity<Void> likeReview(@PathVariable Long reviewId,
                                           @RequestParam Long userId) {
        reviewService.likeReview(reviewId, userId);
        return ResponseEntity.ok().build();
    }


}
