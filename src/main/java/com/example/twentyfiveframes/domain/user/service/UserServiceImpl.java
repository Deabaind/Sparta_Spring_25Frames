package com.example.twentyfiveframes.domain.user.service;

import com.example.twentyfiveframes.domain.user.dto.UserRequestDto;
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
    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("잘못된 이메일 또는 비밀번호입니다.");
        }
    }

    // user 생성
    @Override
    public UserResponseDto.Signup save(UserRequestDto.Signup signupDto) {
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        User user = userRepository.save(new User(
                signupDto.getEmail(),
                signupDto.getPassword(),
                signupDto.getUsername(),
                signupDto.getRole()));
        return new UserResponseDto.Signup("25Frames에 오신 걸 환영합니다", user.getId());
    }

    // user 조회
    @Override
    public UserResponseDto.Get get(Long userId) {
        User user = getUserByUserId(userId);
        return new UserResponseDto.Get(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().toString(),
                user.getCreatedAt());
    }

    @Override
    public void deleteByUserId(Long userId) {
        userRepository.deleteById(userId);
    }
}
