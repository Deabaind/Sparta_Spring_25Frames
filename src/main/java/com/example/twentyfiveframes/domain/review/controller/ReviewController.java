package com.example.twentyfiveframes.domain.review.controller;

import com.example.twentyfiveframes.domain.review.dto.MessageResponseDto;
import com.example.twentyfiveframes.domain.review.dto.ReviewRequestDto;
import com.example.twentyfiveframes.domain.review.dto.ReviewResponseDto;
import com.example.twentyfiveframes.domain.review.dto.ReviewUpdateRequestDto;
import com.example.twentyfiveframes.domain.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Void> createReview(@RequestBody @Valid ReviewRequestDto requestDto,
                                             @AuthenticationPrincipal Long userId) {
        reviewService.createReview(userId, requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 2. 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<MessageResponseDto> updateReview(@PathVariable("reviewId") Long reviewId,
                                                           @RequestBody @Valid ReviewUpdateRequestDto dto,
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

    // 4. 영화별 리뷰 조회 (누구나 접근 가능)
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Page<ReviewResponseDto>> getReviewsByMovie(
            @PathVariable Long movieId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ReviewResponseDto> response = reviewService.getReviewsByMovie(movieId, pageable);
        return ResponseEntity.ok(response);
    }

    // 5. 리뷰 좋아요 등록
    @PostMapping("/{reviewId}/like")
    public ResponseEntity<MessageResponseDto> likeReview(@PathVariable("reviewId") Long reviewId,
                                                         @AuthenticationPrincipal Long userId) {
        reviewService.likeReview(reviewId, userId);
        return ResponseEntity.ok(new MessageResponseDto("리뷰에 좋아요를 눌렀습니다."));
    }

}
