package com.example.twentyfiveframes.domain.movie.controller;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.service.MovieService;
import com.example.twentyfiveframes.domain.user.entity.User;
import com.example.twentyfiveframes.domain.user.entity.UserType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    // 영화 등록
    @PostMapping
    public ResponseEntity<MovieResponseDto.Save> saveMovie(@Valid @RequestBody MovieRequestDto.Save dto) {
        //todo 임시로 로그인 유저로 사용하는 객체, JWT 구현 후 반드시 제거
        User fakeUser = new User("provider@email.com", "TEST1234", "테스트", UserType.ROLE_PROVIDER);
        MovieResponseDto.Save response = movieService.saveMovie(fakeUser, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 영화 전체 조회
    @GetMapping
    public ResponseEntity<Page<MovieResponseDto.GetAll>> getAllMovies(
            @PageableDefault(sort = "releaseDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MovieResponseDto.GetAll> response = movieService.getAllMovies(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 영화 단건 조회
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieResponseDto.Get> getMovie(@PathVariable("movieId") Long movieId) {
        MovieResponseDto.Get response = movieService.getMovie(movieId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 영화 수정
    @PatchMapping("/{movieId}")
    public ResponseEntity<String> updateMovie(@PathVariable Long movieId,
                                              @Valid @RequestBody MovieRequestDto.Update dto) {
        movieService.updateMovie(movieId, dto);

        return ResponseEntity.status(HttpStatus.OK).body("영화 정보가 수정되었습니다.");
    }

    // 영화 삭제
    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long movieId) {
        movieService.deleteMove(movieId);

        return ResponseEntity.status(HttpStatus.OK).body("등록된 영화가 삭제되었습니다.");
    }

}
