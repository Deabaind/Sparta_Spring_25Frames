package com.example.twentyfiveframes.domain.movie.entity;

import com.example.twentyfiveframes.domain.common.BaseEntity;
import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
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
    private Integer ageLimit; // 예: 12, 15, 19

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovieGenre genre;

    @Column(nullable = false)
    private Integer runningTime; // 단위: 분

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private Double averageRating = -1.0; // 평점이 없을 경우 -1 반환

    @Column(nullable = false)
    private Long totalViews = 0L;

    public Movie(MovieRequestDto.Create dto) {
//        this.userId = userId; todo 로그인 유저 추가
        this.title = dto.getTitle();
        this.summary = dto.getSummary();
        this.director = dto.getDirector();
        this.ageLimit = dto.getAgeLimit();
        this.genre = dto.getGenre();
        this.runningTime = dto.getRunningTime();
        this.releaseDate = dto.getReleaseDate();
    }

}