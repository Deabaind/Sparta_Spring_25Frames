package com.example.twentyfiveframes.domain.movie.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MovieGenre {
    ACTION("ACTION"),
    COMEDY("COMEDY"),
    DRAMA("DRAMA"),
    SF("SF"),
    FANTASY("FANTASY"),
    HORROR("HORROR"),
    ETC("ETC");

    private final String genre;

    public static MovieGenre of(String genre) {
        return Arrays.stream(MovieGenre.values())
                .filter(r -> r.name().equalsIgnoreCase(genre))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 입력 값이 아닙니다."));
    }
}
