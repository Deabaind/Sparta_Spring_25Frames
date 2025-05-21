package com.example.twentyfiveframes.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

public class UserResponseDto {

    @Getter
    @RequiredArgsConstructor
    public static class Signup {
        private final String message;
        private final Long userId;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Get {
        private final Long userId;
        private final String email;
        private final String name;
        private final String userType;

//        @JsonFormat(pattern = "yyyy-MM-dd")
        private final LocalDateTime createdAt;
    }
}
