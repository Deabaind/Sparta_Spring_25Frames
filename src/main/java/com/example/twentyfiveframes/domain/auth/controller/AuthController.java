package com.example.twentyfiveframes.domain.auth.controller;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;
import com.example.twentyfiveframes.domain.auth.dto.AuthResponseDto;
import com.example.twentyfiveframes.domain.auth.service.AuthService;
import com.example.twentyfiveframes.domain.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto.Signup> signup(@Valid @RequestBody AuthRequestDto.Signup signup) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(signup));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto.Login> login(@Valid @RequestBody AuthRequestDto.Login login) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(login));
    }
}
