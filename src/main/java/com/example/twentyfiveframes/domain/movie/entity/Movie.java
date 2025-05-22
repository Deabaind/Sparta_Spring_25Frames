package com.example.twentyfiveframes.domain.movie.entity;

import com.example.twentyfiveframes.domain.common.BaseEntity;
import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    private Double averageRating;

    @Column(nullable = false)
    private Long totalViews = 0L;

    public Movie(User user, MovieRequestDto.Save dto) {
        this.user = user;
        this.title = dto.getTitle();
        this.summary = dto.getSummary();
        this.director = dto.getDirector();
        this.ageLimit = dto.getAgeLimit();
        this.genre = dto.getGenre();
        this.runningTime = dto.getRunningTime();
        this.releaseDate = dto.getReleaseDate();
    }

    public void update(MovieRequestDto.Update dto) {
        if (dto.getTitle() != null) this.title = dto.getTitle();
        if (dto.getSummary() != null) this.summary = dto.getSummary();
        if (dto.getDirector() != null) this.director = dto.getDirector();
        if (dto.getAgeLimit() != null) this.ageLimit = dto.getAgeLimit();
        if (dto.getGenre() != null) this.genre = dto.getGenre();
        if (dto.getRunningTime() != null) this.runningTime = dto.getRunningTime();
    }

    public void updateAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void addViews(Long count) {
        this.totalViews += count;
    }
}