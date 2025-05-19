package com.example.twentyfiveframes.domain.user.service;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;
import com.example.twentyfiveframes.domain.user.dto.UserResponseDto;
import com.example.twentyfiveframes.domain.user.entity.User;
import com.example.twentyfiveframes.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // id로 user 객체 조회
    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
    }

    // user 생성
    @Override
    public UserResponseDto.Signup save(AuthRequestDto.Signup signupDto) {
        User user = userRepository.save(new User(
                signupDto.getEmail(),
                signupDto.getPassword(),
                signupDto.getUsername(),
                signupDto.getRole()));
        return new UserResponseDto.Signup(user.getId());
    }

    // user 조회
    @Override
    public UserResponseDto.Get get(Long userId) {
        User user = getUser(userId);
        return new UserResponseDto.Get(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().toString(),
                user.getCreatedAt());
    }
}
