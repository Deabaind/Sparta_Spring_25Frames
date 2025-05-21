package com.example.twentyfiveframes.domain.review.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieDetailResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.movie.service.MovieService;
import com.example.twentyfiveframes.domain.CustomException;
import com.example.twentyfiveframes.domain.ErrorCode;
import com.example.twentyfiveframes.domain.review.dto.ReviewRequestDto;
import com.example.twentyfiveframes.domain.review.dto.ReviewResponseDto;
import com.example.twentyfiveframes.domain.review.dto.ReviewUpdateRequestDto;
import com.example.twentyfiveframes.domain.review.dto.ReviewWithLikeDto;
import com.example.twentyfiveframes.domain.review.entity.Review;
import com.example.twentyfiveframes.domain.review.repository.ReviewRepository;
import com.example.twentyfiveframes.domain.reviewLike.dto.ReviewLikeCountDto;
import com.example.twentyfiveframes.domain.reviewLike.entity.ReviewLike;
import com.example.twentyfiveframes.domain.reviewLike.repository.ReviewLikeRepository;
import com.example.twentyfiveframes.domain.user.entity.User;
import com.example.twentyfiveframes.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private static final String MOVIE_RANKING_KEY = "movieRanking";
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final UserService userService;
    private final MovieService movieService;


    /**
     * 리뷰 등록 + 영화 평균 평점 갱신 + 랭킹 캐시 무효화
     */
    @CacheEvict(value = MOVIE_RANKING_KEY, allEntries = true)
    public void createReview(Long userId, ReviewRequestDto requestDto) {
        User user = userService.getUserByUserId(userId);
        Movie movie = movieService.getMovieById(requestDto.getMovieId());

        // 리뷰 저장
        Review review = new Review(user, movie, requestDto.getRating(), requestDto.getContent());
        reviewRepository.save(review);

        // 평균 평점 계산 후 영화에 반영
        Double avgRating = reviewRepository.findAverageRatingByMovieId(movie.getId());
        if (avgRating != null) {
            movie.updateAverageRating(avgRating);
        }
    }
    /**
     * 리뷰 수정
     *
     */
    public void updateReview(Long reviewId, ReviewUpdateRequestDto dto, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.REVIEW_FORBIDDEN);
        }

        review.update(dto.getRating(), dto.getContent());
    }
    /**
     * 리뷰 삭제
     *
     *
     */
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.REVIEW_FORBIDDEN);
        }

        reviewRepository.delete(review);
    }
    /**
     * 특정 영화에 대한 모든 리뷰 조회
     *
     *
     */
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getAllReviewsByMovie(Long movieId) {
        return reviewRepository.findAllByMovieId(movieId).stream()
                .map(review -> new ReviewResponseDto(
                        review.getId(),
                        review.getUser().getId(),
                        review.getUser().getUsername(),
                        review.getMovie().getId(),
                        review.getRating(),
                        review.getContent()
                )).collect(Collectors.toList());
    }

    //리뷰 좋아요 등록
    public void likeReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        User user = userService.getUserByUserId(userId);

        boolean exists = reviewLikeRepository.existsByReviewAndUser(review, user);
        if (exists) {
            throw new CustomException(ErrorCode.ALREADY_LIKED);
        }


        ReviewLike like = new ReviewLike(review, user);
        reviewLikeRepository.save(like);
    }



}
