package com.example.twentyfiveframes.domain.movie.entity;

import com.example.twentyfiveframes.domain.common.BaseEntity;
import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo: User 엔티티와 연관 관계
    private Long userId;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(nullable = false, length = 30)
    private String director;

    private Integer ageLimit; // 시청 연령 제한 (예: 12, 15, 19), 시청 연령 제한도 없을 수 있으니 nullable을 true로 변경했습니다.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovieGenre genre;

    @Column(nullable = false)
    private Integer runningTime; // 단위: 분

    @Column(nullable = false)
    private LocalDate releaseDate;

    private Double averageRating; //todo 리뷰를 활용해 평균평점 계산하기, 리뷰 repository 추가 후 반영

    @Column(nullable = false)
    private Long totalViews = 0L;



    public Movie(User user, MovieRequestDto.Save dto) {
        this.userId = user.getId();
        this.title = dto.getTitle();
        this.summary = dto.getSummary();
        this.director = dto.getDirector();
        this.ageLimit = dto.getAgeLimit();
        this.genre = dto.getGenre();
        this.runningTime = dto.getRunningTime();
        this.releaseDate = dto.getReleaseDate();
    }

}