package com.example.twentyfiveframes.domain.auth.service;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;
import com.example.twentyfiveframes.domain.user.service.UserService;
import com.example.twentyfiveframes.security.CustomUserDetails;
import com.example.twentyfiveframes.security.CustomUserDetailsService;
import com.example.twentyfiveframes.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // 로그인
    @Override
    public void login(AuthRequestDto.Login loginRequest) {
        // 인증 객체 생성
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // 인증 객체 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);
        authentication.getName()
        String accessToken = jwtService.createAccessToken(authentication);
        String refreshToken = jwtService.createRefreshToken(authentication);
    }
}
