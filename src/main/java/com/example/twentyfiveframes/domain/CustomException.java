package com.example.twentyfiveframes.domain.review;


import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // 부모 클래스에도 메시지 전달
        this.errorCode = errorCode;
    }
}
