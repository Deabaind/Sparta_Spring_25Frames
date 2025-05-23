package com.example.twentyfiveframes.domain.auth.service;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;
import com.example.twentyfiveframes.domain.auth.dto.AuthResponseDto;
import com.example.twentyfiveframes.domain.user.dto.UserRequestDto;
import com.example.twentyfiveframes.domain.user.dto.UserResponseDto;
import com.example.twentyfiveframes.domain.user.entity.User;
import com.example.twentyfiveframes.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public UserResponseDto.Signup signup(AuthRequestDto.Signup signupRequest) {
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        UserRequestDto.Signup signupDto = new UserRequestDto.Signup(
                signupRequest.getEmail(),
                encodedPassword,
                signupRequest.getUsername(),
                signupRequest.getRole());
        return userService.save(signupDto);
    }

    // 로그인
    @Override
    public AuthResponseDto.Login login(AuthRequestDto.Login loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtService.createAccessToken(authentication);
        String refreshToken = jwtService.createRefreshToken(authentication);

        return new AuthResponseDto.Login(accessToken, "Bearer " + refreshToken);
    }

    // 로그아웃
    @Override
    public void logout(Long userId, HttpServletRequest request) {
        jwtService.logout(userId, request);
    }

    // 탈퇴
    @Override
    public void delete(Long userId, AuthRequestDto.passwordConfirm dto) {
        User user = userService.getUserByUserId(userId);
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        userService.deleteByUserId(userId);
    }


}
