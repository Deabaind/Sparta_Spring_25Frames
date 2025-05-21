package com.example.twentyfiveframes.domain.reviewLike.repository;

import com.example.twentyfiveframes.domain.review.entity.Review;
import com.example.twentyfiveframes.domain.reviewLike.dto.ReviewLikeCountDto;
import com.example.twentyfiveframes.domain.reviewLike.entity.ReviewLike;
import com.example.twentyfiveframes.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    boolean existsByReviewAndUser(Review review, User user);
    int countByReviewId(Long reviewId);

    // ✅ 리뷰 ID 목록에 대해 좋아요 수를 한 번에 조회
    @Query("SELECT new com.example.twentyfiveframes.domain.reviewLike.dto.ReviewLikeCountDto(" +
            "r.review.id, CAST(COUNT(r) AS int)) " +
            "FROM ReviewLike r WHERE r.review.id IN :reviewIds GROUP BY r.review.id")
    List<ReviewLikeCountDto> countLikesForReviewIds(@Param("reviewIds") List<Long> reviewIds);

    @Modifying
    @Query("DELETE FROM ReviewLike rl WHERE rl.review.id = :reviewId")
    void deleteByReviewId(@Param("reviewId") Long reviewId);
}
