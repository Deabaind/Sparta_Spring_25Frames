package com.example.twentyfiveframes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
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
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String fieldName = (fieldError != null) ? fieldError.getField() : "unknown";
        String errorMessage = (fieldError != null) ? fieldError.getDefaultMessage() : "요청 값이 유효하지 않습니다.";

        if ("rating".equals(fieldName)) {
            return ResponseEntity
                    .status(ErrorCode.INVALID_RATING.getStatus())
                    .body(new ErrorResponse(ErrorCode.INVALID_RATING.getCode(), errorMessage));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("VALIDATION_ERROR", errorMessage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> requestExceptionHandler(HttpMessageNotReadableException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("BAD_REQUEST" , "요청 형식이 잘못되었습니다. JSON 구조를 확인하세요." ));
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private String code;
        private String message;
    }

}
