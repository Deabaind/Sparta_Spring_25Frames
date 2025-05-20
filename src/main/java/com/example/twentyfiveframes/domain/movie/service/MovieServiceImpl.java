package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.movie.repository.MovieRepository;
import com.example.twentyfiveframes.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

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
        Movie movie = getMovieById(movieId);

        //todo 리뷰 조회, 리뷰 dto 변환, 아래 return 코드에서 함께 반환

        return MovieResponseDto.Get.from(movie);
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
