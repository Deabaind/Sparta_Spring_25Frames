package com.example.twentyfiveframes.domain.auth.dto;

import com.example.twentyfiveframes.domain.user.entity.UserType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class AuthRequestDto {

    @Getter
    @NoArgsConstructor(force = true)
    @RequiredArgsConstructor
    public static class Signup {

        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일는 필수 입력 항목입니다.")
        private final String email;

        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]{8,20}$",
                message = "비밀번호는 8~20자 사이의 영문자와 숫자만 사용하며, 대문자와 숫자를 각각 1개 이상 포함해야 합니다.")
        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        private final String password;

        @Size(max = 20, message = "20자 이하로 입력할 수 있습니다.")
        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        private final String username;

        @NotNull(message = "사용자 타입은 필수 입력 항목입니다.")
        private final UserType role;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Login {

        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일는 필수 입력 항목입니다.")
        private final String email;

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        private final String password;
    }
}
