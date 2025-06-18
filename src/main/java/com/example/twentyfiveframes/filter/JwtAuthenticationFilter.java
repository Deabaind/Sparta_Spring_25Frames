package com.example.twentyfiveframes.filter;

import com.example.twentyfiveframes.security.CustomUserDetails;
import com.example.twentyfiveframes.domain.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        if (isAuthRequest(method, uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtService.resolveAccessToken(request);
        String refreshToken = jwtService.resolveRefreshToken(request);

        if (StringUtils.hasText(accessToken) && jwtService.validateAccessToken(accessToken)) {
            log.info("AccessToken 인증 성공");

            setAuthentication(accessToken, request);
        } else if (StringUtils.hasText(refreshToken) && jwtService.validateToken(refreshToken)) {
            log.info("RefreshToken 인증 성공");

            String newAccessToken = reissueToken(method, uri, refreshToken, response);
            log.info("Token 재발급 성공");

            setAuthentication(newAccessToken, request);
        }
        filterChain.doFilter(request, response);
    }

    // 인증 객체 재등록
    private void setAuthentication(String token, HttpServletRequest request) {
        CustomUserDetails userDetails = jwtService.getUserDetails(token);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUser().getId(), null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    // 토큰 재발급
    private String reissueToken(String method, String uri, String refreshToken, HttpServletResponse response){
        if (isReissueDisabledRequest(method, uri)) {
            throw new IllegalArgumentException("로그아웃 또는 회원 탈퇴 요청에는 토큰이 재발급되지 않습니다.");
        }

        String newAccessToken = jwtService.createAccessToken(refreshToken);
        String newRefreshToken = jwtService.createRefreshToken(refreshToken);

        response.setHeader("Authorization", newAccessToken);
        response.setHeader("Authorization-refresh", "Bearer " + newRefreshToken);

        return newAccessToken;
    }

    // 회원가입, 로그인 요청 확인
    private boolean isAuthRequest(String method, String uri) {
        return ("POST".equals(method) && "/signup".equals(uri)) ||
                ("POST".equals(method) && "/login".equals(uri));
    }

    // 로그아웃, 회원 탈퇴 요청 확인
    private boolean isReissueDisabledRequest(String method, String uri) {
        return ("POST".equals(method) && "/logout".equals(uri)) ||
                ("DELETE".equals(method) && "/users".equals(uri));
    }
}
