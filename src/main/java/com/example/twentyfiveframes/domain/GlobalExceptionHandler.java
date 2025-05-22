package com.example.twentyfiveframes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(new ErrorResponse(errorCode.getCode(), errorCode.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        // 어떤 필드에서 오류가 났는지 확인
        String fieldName = ex.getBindingResult().getFieldError().getField();
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();

        // 평점 필드인 경우 → INVALID_RATING으로 고정
        if ("rating".equals(fieldName)) {
            return ResponseEntity
                    .status(ErrorCode.INVALID_RATING.getStatus())
                    .body(new ErrorResponse(ErrorCode.INVALID_RATING.getCode(), errorMessage));
        }

        // 기본 처리 (다른 필드의 유효성 에러)
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("VALIDATION_ERROR", errorMessage));
    }

    // 예외 응답 DTO
    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private String code;
        private String message;
    }

}
