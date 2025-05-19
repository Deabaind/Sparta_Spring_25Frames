package com.example.twentyfiveframes.domain.auth.service;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;

public interface AuthService {

    void login(AuthRequestDto.Login loginRequest);
}
