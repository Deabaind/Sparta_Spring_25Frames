package com.example.twentyfiveframes.domain.movie.controller;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // 영화 등록
    @PostMapping
    public ResponseEntity<MovieResponseDto.create> createMovie(@RequestBody MovieRequestDto.Create dto) { //todo 로그인 유저 정보 받기
        MovieResponseDto.create response = movieService.createMovie(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 영화 수정

    // 영화 전체 조회

    // 영화 단건 조회

    // 영화 삭제

}
