package com.example.twentyfiveframes.domain.movie.controller;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.movie.service.MovieServiceImpl;
import com.example.twentyfiveframes.domain.user.entity.User;
import com.example.twentyfiveframes.domain.user.entity.UserType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieServiceImpl MovieServiceImpl;

    // 영화 등록
    @PostMapping
    public ResponseEntity<MovieResponseDto.Save> saveMovie(@Valid @RequestBody MovieRequestDto.Save dto) {
        //todo 임시로 로그인 유저로 사용하는 객체, JWT 구현 후 반드시 제거
        User fakeUser = new User("provider@email.com", "TEST1234", "테스트", UserType.ROLE_PROVIDER);

        MovieResponseDto.Save response = MovieServiceImpl.saveMovie(fakeUser, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 영화 수정

    // 영화 전체 조회

    // 영화 단건 조회

    // 영화 삭제

    // 키워드 기반 영화 검색
    @GetMapping
    public ResponseEntity<List<Movie>> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre) {
        List<Movie> movies = MovieServiceImpl.search(title, genre);
        return ResponseEntity.ok(movies);
    }

    // 인기 검색어 조회
    @GetMapping("/popular")
    public ResponseEntity<List<String>> topKeywords(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(MovieServiceImpl.topKeywords(limit));
    }
}
