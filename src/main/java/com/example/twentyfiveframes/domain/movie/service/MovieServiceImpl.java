package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.movie.repository.MovieRepository;
import com.example.twentyfiveframes.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    // 영화 등록
    @Override
    public MovieResponseDto.create saveMovie(User user, MovieRequestDto.Create dto) {
        Movie movie = new Movie(user, dto);
        movieRepository.save(movie);

        return new MovieResponseDto.create("영화가 등록되었습니다.", movie.getId());
    }

    // 영화 수정

    // 영화 전체 조회

    // 영화 단건 조회

    // 영화 삭제
}
