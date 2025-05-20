package com.example.twentyfiveframes.domain.review.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieDetailResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.movie.repository.MovieRepository;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public void createReview(Long userId, ReviewRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Movie movie = movieRepository.findById(requestDto.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));

        Review review = new Review(user, movie, requestDto.getRating(), requestDto.getContent());
        reviewRepository.save(review);
    }

    public void updateReview(Long reviewId, ReviewUpdateRequestDto dto, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 리뷰만 수정할 수 있습니다.");
        }

        review.update(dto.getRating(), dto.getContent());
    }

    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 리뷰만 삭제할 수 있습니다.");
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
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        boolean exists = reviewLikeRepository.existsByReviewAndUser(review, user);
        if (exists) {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
        }

        ReviewLike like = new ReviewLike(review, user);
        reviewLikeRepository.save(like);
    }

    @Transactional(readOnly = true)
    public ReviewLikeCountDto getReviewLikeCount(Long reviewId) {
        int count = reviewLikeRepository.countByReviewId(reviewId);
        return new ReviewLikeCountDto(reviewId, count);
    }

    //영화 상세 조회 시 리뷰, 좋아요 확인
    public MovieDetailResponseDto getMovieDetail(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));

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
