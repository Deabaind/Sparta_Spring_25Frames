package com.example.twentyfiveframes.domain.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class AuthResponseDto {

    @Getter
    @RequiredArgsConstructor
    public static class Login {
        private final String accessToken;
        private final String refreshToken;
    }
}
