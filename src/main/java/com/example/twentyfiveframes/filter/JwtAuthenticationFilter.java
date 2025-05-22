package com.example.twentyfiveframes.filter;

import com.example.twentyfiveframes.security.CustomUserDetails;
import com.example.twentyfiveframes.security.JwtService;
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

        String accessToken = jwtService.resolveAccessToken(request);
        String refreshToken = jwtService.resolveRefreshToken(request);

        if (StringUtils.hasText(accessToken) && jwtService.validateAccessToken(accessToken)) {
            log.info("accessToken 인증 성공");

            setAuthentication(accessToken, request);
        } else if (StringUtils.hasText(refreshToken) && jwtService.validateToken(refreshToken)) {
            log.info("refreshToken 인증 성공 및 accessToken 재발급");

            String newAccessToken = reissueToken(refreshToken, response);

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
    private String reissueToken(String refreshToken, HttpServletResponse response){

        String newAccessToken = jwtService.createAccessToken(refreshToken);
        String newRefreshToken = jwtService.createRefreshToken(refreshToken);

        response.setHeader("Authorization", newAccessToken);
        response.setHeader("Authorization-refresh", "Bearer " + newRefreshToken);

        return newAccessToken;
    }
}
