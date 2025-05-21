package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.movie.repository.MovieRepository;
import com.example.twentyfiveframes.domain.review.dto.ReviewWithLikeDto;
import com.example.twentyfiveframes.domain.review.entity.Review;
import com.example.twentyfiveframes.domain.review.repository.ReviewRepository;
import com.example.twentyfiveframes.domain.review.service.ReviewService;
import com.example.twentyfiveframes.domain.reviewLike.dto.ReviewLikeCountDto;
import com.example.twentyfiveframes.domain.reviewLike.repository.ReviewLikeRepository;
import com.example.twentyfiveframes.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    // movieId로 Movie 조회
    @Override
    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));
    }

    // 영화 등록
    @Override
    public MovieResponseDto.Save saveMovie(User user, MovieRequestDto.Save dto) {
        Movie movie = new Movie(user, dto);
        movieRepository.save(movie);

        return new MovieResponseDto.Save("영화가 등록되었습니다.", movie.getId());
    }

    // 영화 전체 조회
    @Override
    public Page<MovieResponseDto.GetAll> getAllMovies(Pageable pageable) {

        return movieRepository.findAll(pageable)
                .map(MovieResponseDto.GetAll::from);
    }
    
    // 영화 단건 조회
    @Override
    public MovieResponseDto.Get getMovie(Long movieId) {
        // 1. 영화 조회
        Movie movie = getMovieById(movieId);

        // 2. 해당 영화의 모든 리뷰 조회
        List<Review> reviews = reviewRepository.findAllByMovieId(movieId);
        List<Long> reviewIds = reviews.stream()
                .map(Review::getId)
                .toList();

        // 3. 리뷰 ID 기준 좋아요 수 한 번에 조회 → Map으로 변환
        Map<Long, Integer> likeCountMap = reviewLikeRepository
                .countLikesForReviewIds(reviewIds)
                .stream()
                .collect(Collectors.toMap(
                        ReviewLikeCountDto::getReviewId,
                        ReviewLikeCountDto::getLikeCount
                ));

        // 4. 리뷰 DTO 구성
        List<ReviewWithLikeDto> reviewDtos = reviews.stream()
                .map(review -> new ReviewWithLikeDto(
                        review.getId(),
                        review.getUser().getId(),
                        review.getUser().getUsername(),
                        review.getRating(),
                        review.getContent(),
                        likeCountMap.getOrDefault(review.getId(), 0)
                ))
                .collect(Collectors.toList());

        // 5. 최종 응답
        return new MovieResponseDto.Get(
                movie.getId(),
                movie.getTitle(),
                movie.getSummary(),
                movie.getDirector(),
                movie.getAgeLimit(),
                movie.getGenre().toString(),
                movie.getRunningTime(),
                movie.getReleaseDate(),
                movie.getAverageRating(),
                movie.getCreatedAt(),
                movie.getUpdatedAt(),
                reviewDtos
        );
    }
    // 영화 수정
    @Override
    public void updateMovie(Long movieId, MovieRequestDto.Update dto) {
        Movie movie = getMovieById(movieId);
        movie.update(dto);
    }

    // 영화 삭제
    @Override
    public void deleteMove(Long movieId) {
        Movie movie = getMovieById(movieId);
        movie.softDelete();
    }

}
