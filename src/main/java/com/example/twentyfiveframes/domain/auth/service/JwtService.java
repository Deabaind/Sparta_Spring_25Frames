package com.example.twentyfiveframes.domain.auth.service;

import com.example.twentyfiveframes.domain.user.entity.User;
import com.example.twentyfiveframes.domain.user.service.UserService;
import com.example.twentyfiveframes.security.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Getter
@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    private final UserService userService;
    private final AuthRedisService authRedisService;

    @Value("${jwt.secretKey}")
    private String secretKeyString;

    private Key secretKey;

    // 10 / 분 60 / 초 1000
    private final long accessTokenValidMillionSecond = 10 * 60 * 1000;

    // 10 / 시간 60 / 분 60 / 초 1000
    private final long refreshTokenValidMillionSecond = 10 * 60 * 60 * 1000;

    private final long refreshTokenValidHours = refreshTokenValidMillionSecond / 3600000;

    // 암호화 키 생성
    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    // AccessToken 생성
    public String createAccessToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenValidMillionSecond);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("tokenType", "access")
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // RefreshToken 생성
    public String createRefreshToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenValidMillionSecond);

        String refreshToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("tokenType", "refresh")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        authRedisService.save(
                user.getId().toString(),
                refreshToken,
                refreshTokenValidHours,
                TimeUnit.HOURS
        );
        return refreshToken;
    }

    // accessToken 재발급
    public String createAccessToken(String token) {
        CustomUserDetails userDetails = getUserDetails(token);

        User user = userDetails.getUser();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenValidMillionSecond);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("tokenType", "access")
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // refreshToken 재발급
    public String createRefreshToken(String token) {
        String userId = getUserId(token);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenValidMillionSecond);

        String refreshToken = Jwts.builder()
                .setSubject(userId)
                .claim("tokenType", "refresh")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        // redis에 새로운 refreshToken 저장
        authRedisService.update(
                userId,
                refreshToken,
                refreshTokenValidHours,
                TimeUnit.HOURS
        );

        return refreshToken;
    }

    // 요청 헤더에서 accessToken 가져오기
    public String resolveAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
            return accessToken.substring(7);
        }
        return null;
    }

    // 요청 헤더에서 refreshToken 가져오기
    public String resolveRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Authorization-refresh");
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith("Bearer ")) {
            return refreshToken.substring(7);
        }
        return null;
    }

    // token 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            log.info("토큰 검증 성공");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // accessToken 검증
    public boolean validateAccessToken(String token) {
        if (!validateToken(token)) {
            return false;
        }
        if (authRedisService.validAccessToken(token)) {
            return false;
        }
        String userId = getUserId(token);
        if (authRedisService.validRefreshToken(userId, token)) {
            return false;
        }

        return true;
    }

    // token에서 user 꺼내기
    public CustomUserDetails getUserDetails(String token) {
        String userId = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        User user = userService.getUserByUserId(Long.valueOf(userId));
        return new CustomUserDetails(user);
    }

    // token에서 String 타입으로 userId 꺼내기
    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public void logout(Long userId, HttpServletRequest request) {
        authRedisService.delete(userId.toString());
        String refreshToken = resolveAccessToken(request);
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody()
                .getExpiration();
        Date now = new Date();
        long time = (expiration.getTime() - now.getTime()) / 60000;
        authRedisService.blackList(refreshToken, time, TimeUnit.MINUTES);
    }
}
