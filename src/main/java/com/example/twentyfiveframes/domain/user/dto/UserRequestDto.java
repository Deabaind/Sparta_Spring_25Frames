package com.example.twentyfiveframes.domain.user.dto;

import com.example.twentyfiveframes.domain.user.entity.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class UserRequestDto {

    @Getter
    @NoArgsConstructor(force = true)
    @RequiredArgsConstructor
    public static class Signup {

        private final String email;

        private final String password;

        private final String username;

        private final UserType role;

    }
}
