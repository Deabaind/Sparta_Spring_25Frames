package com.example.twentyfiveframes.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserResponseDto {

    @Getter
    @RequiredArgsConstructor
    public static class Signup {

        private final Long userId;
    }
}
