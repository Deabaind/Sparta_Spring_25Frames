package com.example.twentyfiveframes.filter;

import com.example.twentyfiveframes.security.CustomUserDetails;
import com.example.twentyfiveframes.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtService.resolveAccessToken(request);
        String refreshToken = jwtService.resolveRefreshToken(request);

        if (StringUtils.hasText(accessToken) && jwtService.validateAccessToken(accessToken)) {
            log.info("accessToken 인증 성공");
            setAuthentication(accessToken, response);
        } else if (StringUtils.hasText(refreshToken) && jwtService.validateToken(refreshToken)) {
            log.info("refreshToken 인증 성공 및 accessToken 재발급");
            // todo 코드 수정
            String newAccessToken = "토큰 재발급 코드";

            setAuthentication(newAccessToken, response);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token, HttpServletResponse response) {
        CustomUserDetails userDetails = jwtService.
    }
}
