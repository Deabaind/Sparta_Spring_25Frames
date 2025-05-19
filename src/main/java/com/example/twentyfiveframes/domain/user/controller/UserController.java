package com.example.twentyfiveframes.domain.user.controller;

import com.example.twentyfiveframes.domain.user.dto.UserResponseDto;
import com.example.twentyfiveframes.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인 유저 정보 조회
    @GetMapping
    public ResponseEntity<UserResponseDto.Get> findUser() {
        // todo 로그인 구현 후 수정
        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.OK).body(userService.get(userId));
    }
}
