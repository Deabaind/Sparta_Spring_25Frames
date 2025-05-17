package com.example.twentyfiveframes.domain.movie.entity;

import com.example.twentyfiveframes.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //  User 엔티티와 연관 관계
    private Long userId;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(nullable = false, length = 30)
    private String director;

    @Column(nullable = false)
    private int ageLimit; // 예: 12, 15, 19

    //@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private String genre; // enum 생성 후 변경

    @Column(nullable = false)
    private int runningTime; // 단위: 분

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private Double averageRating;

    @Column(nullable = false)
    private Long totalViews;
}
