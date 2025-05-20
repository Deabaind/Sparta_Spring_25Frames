package com.example.twentyfiveframes.domain.reviewLike.repository;

import com.example.twentyfiveframes.domain.review.entity.Review;
import com.example.twentyfiveframes.domain.reviewLike.entity.ReviewLike;
import com.example.twentyfiveframes.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    boolean existsByReviewAndUser(Review review, User user);
    int countByReviewId(Long reviewId);
}
