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
    

}
