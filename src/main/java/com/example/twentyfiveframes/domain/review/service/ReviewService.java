package com.example.twentyfiveframes.domain.review.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieDetailResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.movie.repository.MovieRepository;
import com.example.twentyfiveframes.domain.movie.service.MovieService;
import com.example.twentyfiveframes.domain.review.CustomException;
import com.example.twentyfiveframes.domain.review.ErrorCode;
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
import com.example.twentyfiveframes.domain.user.repository.UserRepository;
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

    @CacheEvict(value = MOVIE_RANKING_KEY, allEntries = true)
    public void createReview(Long userId, ReviewRequestDto requestDto) {
        User user = userService.getUserByUserId(userId);
        Movie movie = movieService.getMovieById(requestDto.getMovieId());

        Review review = new Review(user, movie, requestDto.getRating(), requestDto.getContent());
        reviewRepository.save(review);
    }

    public void updateReview(Long reviewId, ReviewUpdateRequestDto dto, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.REVIEW_FORBIDDEN);
        }

        review.update(dto.getRating(), dto.getContent());
    }

    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.REVIEW_FORBIDDEN);
        }

        reviewRepository.delete(review);
    }

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

    //영화 상세 조회 시 리뷰, 좋아요 확인
    public MovieDetailResponseDto getMovieDetail(Long movieId, ReviewRequestDto requestDto) {
        Movie movie = movieService.getMovieById(requestDto.getMovieId());

        List<Review> reviews = reviewRepository.findAllByMovieId(movieId);

        List<ReviewWithLikeDto> reviewDtos = reviews.stream()
                .map(review -> new ReviewWithLikeDto(
                        review.getId(),
                        review.getUser().getId(),
                        review.getUser().getUsername(),
                        review.getRating(),
                        review.getContent(),
                        reviewLikeRepository.countByReviewId(review.getId())
                )).toList();

        return new MovieDetailResponseDto(
                movie.getId(),
                movie.getTitle(),
                movie.getGenre(),
                movie.getDirector(),
                reviewDtos
        );
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(review -> new ReviewResponseDto(
                        review.getId(),
                        review.getUser().getId(),
                        review.getUser().getUsername(),
                        review.getMovie().getId(),
                        review.getRating(),
                        review.getContent()
                ))
                .collect(Collectors.toList());
    }
}
