package com.example.twentyfiveframes.domain.user.service;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;
import com.example.twentyfiveframes.domain.user.dto.UserResponseDto;

public interface UserService {

    // user 생성
    UserResponseDto.Signup save(AuthRequestDto.Signup signupDto);
}
