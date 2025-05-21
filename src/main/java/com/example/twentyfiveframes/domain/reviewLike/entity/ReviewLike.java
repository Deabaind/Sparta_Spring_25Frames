package com.example.twentyfiveframes.domain.reviewLike.entity;

import com.example.twentyfiveframes.domain.review.entity.Review;
import com.example.twentyfiveframes.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne
    private Review review;

    @JoinColumn
    @ManyToOne
    private User user;

    @CreatedDate
    private LocalDateTime createdAt;

    public ReviewLike(Review review, User user) {
        this.review = review;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }
}
