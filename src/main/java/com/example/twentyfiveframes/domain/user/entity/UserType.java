package com.example.twentyfiveframes.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    ROLE_USER("USER"),
    ROLE_PROVIDER ("PROVIDER");

    private final String userType;
}
