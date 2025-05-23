package com.example.twentyfiveframes.domain.auth.service;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;
import com.example.twentyfiveframes.domain.auth.dto.AuthResponseDto;
import com.example.twentyfiveframes.domain.user.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    // 회원 가입
    UserResponseDto.Signup signup(AuthRequestDto.Signup signupRequest) ;

    // 로그인
    AuthResponseDto.Login login(AuthRequestDto.Login loginRequest);

    // 로그아웃
    void logout(Long userId, HttpServletRequest request);

    // 탈퇴
    void delete(Long userId, AuthRequestDto.passwordConfirm dto);
}
