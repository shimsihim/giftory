package com.side.giftory.security.jwt;

// com.example.config.security.jwt.JwtAuthenticationFilter.java
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String JWT_COOKIE_NAME = "accessToken"; // 쿠키에 저장한 JWT 이름

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 요청 쿠키에서 JWT 토큰 추출
        String jwt = resolveToken(request);

        // 2. 토큰 유효성 검증 및 인증 처리
        if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
            // 토큰이 유효하면 인증 객체(Authentication) 생성
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);

            // SecurityContextHolder에 인증 정보 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // 요청에서 "accessToken" 쿠키 값을 추출하는 메서드
    private String resolveToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        // 쿠키 배열에서 "accessToken" 이름의 쿠키를 찾습니다.
        return Arrays.stream(request.getCookies())
                .filter(cookie -> JWT_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}