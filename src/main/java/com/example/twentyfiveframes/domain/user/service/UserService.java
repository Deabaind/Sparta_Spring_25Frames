package com.example.twentyfiveframes.domain.user.service;

import com.example.twentyfiveframes.domain.user.dto.UserRequestDto;
import com.example.twentyfiveframes.domain.user.dto.UserResponseDto;
import com.example.twentyfiveframes.domain.user.entity.User;

public interface UserService {

    // id로 user 객체 조회
    User getUserByUserId(Long userId);

    // eamil로 user 객체 조회
    User getUserByEmail(String email);

    // user 생성
    UserResponseDto.Signup save(UserRequestDto.Signup signupDto);

    // user 정보 조회
    UserResponseDto.Get get(Long userId);

    // user 삭제
    void deleteByUserId(Long userId);
}
