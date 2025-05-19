package com.example.twentyfiveframes.domain.auth.service;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;
import com.example.twentyfiveframes.domain.auth.dto.AuthResponseDto;
import com.example.twentyfiveframes.domain.user.dto.UserResponseDto;

public interface AuthService {

    UserResponseDto.Signup signup(AuthRequestDto.Signup signupRequest) ;

    AuthResponseDto.Login login(AuthRequestDto.Login loginRequest);
}
