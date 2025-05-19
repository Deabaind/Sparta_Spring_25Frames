package com.example.twentyfiveframes.domain.user.service;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;
import com.example.twentyfiveframes.domain.user.dto.UserResponseDto;
import com.example.twentyfiveframes.domain.user.entity.User;
import com.example.twentyfiveframes.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // user 생성
    public UserResponseDto.Signup save(AuthRequestDto.Signup signupDto) {
        User user = userRepository.save(new User(
                signupDto.getEmail(),
                signupDto.getPassword(),
                signupDto.getUsername(),
                signupDto.getRole()));
        return new UserResponseDto.Signup(user.getId());
    }

    // user 수정 로직
    public void update() {

    }
}
