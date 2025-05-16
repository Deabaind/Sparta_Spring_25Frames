package com.example.twentyfiveframes.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    ROLE_USER("USER"),
    ROLE_DISTRIBUTOR("DISTRIBUTOR");

    private final String userType;
}
