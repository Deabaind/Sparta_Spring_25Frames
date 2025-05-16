package com.example.twentyfiveframes.domain.review.entity;

import com.example.twentyfiveframes.domain.common.BaseEntity;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne
    private User user;

    @JoinColumn
    @ManyToOne
    private Movie movie;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private String content;
}
