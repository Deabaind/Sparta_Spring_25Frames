package com.example.twentyfiveframes.domain.review;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    REVIEW_NOT_FOUND("REVIEW-001", "리뷰를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    REVIEW_FORBIDDEN("REVIEW-002", "본인의 리뷰만 수정/삭제할 수 있습니다.", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND("USER-001", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    MOVIE_NOT_FOUND("MOVIE-001", "존재하지 않는 영화입니다.", HttpStatus.NOT_FOUND),
    ALREADY_LIKED("REVIEW-003", "이미 좋아요를 눌렀습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
