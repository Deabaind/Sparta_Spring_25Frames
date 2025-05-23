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

    public Movie(User user, String title, String summary, String director, Integer ageLimit, MovieGenre genre,
                 Integer runningTime, LocalDate releaseDate) {
        this.user = user;
        this.title = title;
        this.summary = summary;
        this.director = director;
        this.ageLimit = ageLimit;
        this.genre = genre;
        this.runningTime = runningTime;
        this.releaseDate = releaseDate;
    }

    public void updateTitle(String title) {this.title = title;}
    public void updateSummary(String summary) {this.summary = summary;}
    public void updateDirector(String director) {this.director = director;}
    public void updateAgeLimit(Integer ageLimit) {this.ageLimit = ageLimit;}
    public void updateMovieGenre(MovieGenre genre) {this.genre = genre;}
    public void updateRunningTime(Integer runningTime) {this.runningTime = runningTime;}
    public void updateAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void addViews(Long count) {
        this.totalViews += count;
    }
}