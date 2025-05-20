package com.example.twentyfiveframes.domain.auth.service;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;
import com.example.twentyfiveframes.domain.auth.dto.AuthResponseDto;
import com.example.twentyfiveframes.domain.user.dto.UserResponseDto;

public interface AuthService {

    // 회원 가입
    UserResponseDto.Signup signup(AuthRequestDto.Signup signupRequest) ;

    // 로그인
    AuthResponseDto.Login login(AuthRequestDto.Login loginRequest);
}
