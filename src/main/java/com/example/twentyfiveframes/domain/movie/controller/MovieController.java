package com.example.twentyfiveframes.domain.movie.controller;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.service.MovieService;
import com.example.twentyfiveframes.domain.user.entity.User;
import com.example.twentyfiveframes.domain.user.entity.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    // 영화 등록
    @PostMapping
    public ResponseEntity<MovieResponseDto.Save> saveMovie(@RequestBody MovieRequestDto.Save dto) {
        //todo 임시로 로그인 유저로 사용하는 객체, JWT 구현 후 반드시 제거
        User fakeUser = new User("provider@email.com", "TEST1234", "테스트", UserType.ROLE_PROVIDER);

        MovieResponseDto.Save response = movieService.saveMovie(fakeUser, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 영화 수정

    // 영화 전체 조회

    // 영화 단건 조회

    // 영화 삭제

}
