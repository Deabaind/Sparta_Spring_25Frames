package com.example.twentyfiveframes.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    REVIEW_NOT_FOUND("REVIEW-001", "리뷰를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    REVIEW_FORBIDDEN("REVIEW-002", "본인의 리뷰만 수정/삭제할 수 있습니다.", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND("USER-001", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    FORBIDDEN("COMMON-403", "권한이 없습니다.", HttpStatus.FORBIDDEN),
    INVALID_RATING("REVIEW-004", "평점은 1점에서 5점 사이여야 합니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("COMMON-401", "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
    ALREADY_LIKED("REVIEW-003", "이미 좋아요를 눌렀습니다.", HttpStatus.BAD_REQUEST),

    //MOVIE
    MOVIE_NOT_FOUND("MOVIE-001", "존재하지 않는 영화입니다.", HttpStatus.NOT_FOUND),
    MOVIE_ACCESS_DENIED("MOVIE-002","영화에 등록할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    MOVIE_UPDATE_DENIED("MOVIE-002","영화를 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    MOVIE_DELETE_DENIED("MOVIE-002","영화를 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN),


    ;

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
