package com.example.twentyfiveframes.security;

import com.example.twentyfiveframes.domain.user.entity.User;
import com.example.twentyfiveframes.domain.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Getter
@Service
public class JwtService {

    private final UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    @Value("${jwt.secretKey}")
    private String secretKeyString;

    private Key secretKey;

    private final long accessTokenValidMillionSecond = 600000;
    private final long refreshTokenValidMillionSecond = 3600000;

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

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("tokenType", "refresh")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
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
        CustomUserDetails userDetails = getUserDetails(token);
        User user = userDetails.getUser();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenValidMillionSecond);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("tokenType", "refresh")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
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
                    .parseClaimsJwt(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // accessToken 검증
    public boolean validateAccessToken(String token) {
        try {
            // todo refresh Token 검증 코드
            validateToken(token);

            return true;
        } catch (Exception e) {
            return false;
        }
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
}
