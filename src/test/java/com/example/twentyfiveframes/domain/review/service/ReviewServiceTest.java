package com.example.twentyfiveframes.domain.review.service;


import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.movie.entity.MovieGenre;
import com.example.twentyfiveframes.domain.movie.repository.MovieRepository;
import com.example.twentyfiveframes.domain.movie.service.MovieService;
import com.example.twentyfiveframes.domain.review.dto.ReviewRequestDto;
import com.example.twentyfiveframes.domain.review.dto.ReviewResponseDto;
import com.example.twentyfiveframes.domain.review.dto.ReviewUpdateRequestDto;
import com.example.twentyfiveframes.domain.review.entity.Review;
import com.example.twentyfiveframes.domain.review.repository.ReviewRepository;
import com.example.twentyfiveframes.domain.reviewLike.dto.ReviewLikeCountDto;
import com.example.twentyfiveframes.domain.reviewLike.repository.ReviewLikeRepository;
import com.example.twentyfiveframes.domain.user.entity.User;
import com.example.twentyfiveframes.domain.user.entity.UserType;
import com.example.twentyfiveframes.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(false)
public class ReviewServiceTest {

    @Autowired private ReviewService reviewService;
    @Autowired private UserRepository userRepository;
    @Autowired private MovieRepository movieRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private ReviewLikeRepository reviewLikeRepository;

    private User user;
    private Movie movie;

    @BeforeEach
    void setup() {
        reviewLikeRepository.deleteAll();
        reviewRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();

        user = new User("test@test.com", "1234", "test", UserType.ROLE_USER);
        injectId(user, 1L);
        user = userRepository.save(user);

        MovieRequestDto.Save movieDto = new MovieRequestDto.Save(
                "테스트 영화",
                "즐거리",
                "감독",
                15,
                MovieGenre.ACTION,
                120,
                LocalDate.of(2023, 10, 10)
        );

        movie = new Movie(user, movieDto);
        movie = movieRepository.save(movie);
    }
    private void injectId(Object target, Long idValue) {
        Field field = ReflectionUtils.findField(target.getClass(), "id");
        if (field != null) {
            field.setAccessible(true);
            ReflectionUtils.setField(field, target, idValue);
        }
    }

    @Test
    void 리뷰_등록() {
        ReviewRequestDto request = new ReviewRequestDto(movie.getId(), 5, "재밌어요");
        reviewService.createReview(user.getId(), request);

        Movie updatedMovie = movieRepository.findById(movie.getId()).orElseThrow();
        assertThat(updatedMovie.getAverageRating()).isEqualTo(5.0); // 평균 평점 확인

        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(1);
        assertThat(reviews.get(0).getContent()).isEqualTo("재밌어요");
    }

    @Test
    void 리뷰_조회_전체() {
        Review review = reviewRepository.save(new Review(user, movie, 5, "재밌어요"));
        List<ReviewResponseDto> responses = reviewService.getAllReviews();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getContent()).isEqualTo("재밌어요");
    }

    @Test
    void 리뷰_조회_영화별() {
        reviewRepository.save(new Review(user, movie, 5, "영화별 리뷰"));
        List<ReviewResponseDto> responses = reviewService.getAllReviewsByMovie(movie.getId());

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getContent()).isEqualTo("영화별 리뷰");
    }

    @Test
    void 리뷰_수정() {
        Review review = reviewRepository.save(new Review(user, movie, 5, "또 볼래요"));
        ReviewUpdateRequestDto update = new ReviewUpdateRequestDto();  // 수정용 DTO 사용
        update.setRating(4);
        update.setContent("수정된 리뷰");

        reviewService.updateReview(review.getId(), update, user.getId());

        Review updated = reviewRepository.findById(review.getId()).orElseThrow();
        assertThat(updated.getContent()).isEqualTo("수정된 리뷰");
    }

    @Test
    void 리뷰_삭제() {
        Review review = reviewRepository.save(new Review(user, movie, 2, "삭제할 리뷰"));

        reviewService.deleteReview(review.getId(), user.getId());

        Review deleted = reviewRepository.findById(review.getId()).orElseThrow();
        assertThat(deleted.getDeletedAt()).isNotNull();
    }

    @Test
    void 리뷰_좋아요() {
        Review review = reviewRepository.save(new Review(user, movie, 5, "좋아요 리뷰"));

        reviewService.likeReview(review.getId(), user.getId());

        int likeCount = reviewService.getReviewLikeCount(review.getId()).getLikeCount(); // 메서드 이름이 countLikes 맞는지 확인
        assertThat(likeCount).isEqualTo(1);
    }
}