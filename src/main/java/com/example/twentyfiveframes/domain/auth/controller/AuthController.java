package com.example.twentyfiveframes.domain.auth.controller;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;
import com.example.twentyfiveframes.domain.auth.dto.AuthResponseDto;
import com.example.twentyfiveframes.domain.auth.service.AuthService;
import com.example.twentyfiveframes.domain.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto.Signup> signup(@Valid @RequestBody AuthRequestDto.Signup signup
    ) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(signup));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto.Login> login(@Valid @RequestBody AuthRequestDto.Login login) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(login));
    }

    // 탈퇴
    @DeleteMapping("/users")
    public ResponseEntity<Void> delete(@Valid @RequestBody AuthRequestDto.passwordConfirm dto, @AuthenticationPrincipal Long userId) {
        authService.delete(userId, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
