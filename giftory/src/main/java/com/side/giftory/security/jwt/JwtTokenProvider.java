package com.side.giftory.security.jwt;

import com.side.giftory.security.RoleType;
import com.side.giftory.security.UserPrincipal;
import com.side.giftory.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // 💡 추가
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails; // 💡 추가
import org.springframework.security.core.userdetails.UserDetailsService; // 💡 추가
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final long tokenValidityInMilliseconds;
    private final UserDetailsService userDetailsService; // 💡 UserDetailsService 추가

    // 환경 변수 주입 (application.yml에 설정 필요)
    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                            @Value("${jwt.expiration-seconds}") long expirationSeconds,
                            UserDetailsService userDetailsService) { // 💡 UserDetailsService 주입
        log.warn("secret = {}",secret);
        log.warn("expirationSeconds = {}",expirationSeconds);
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds = expirationSeconds * 1000;
        this.userDetailsService = userDetailsService; // 💡 초기화
    }

    /**
     * JWT 토큰 생성
     * @param user 사용자
     * @return 생성된 JWT 문자열
     */
    public String createToken(User user) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(user.getId().toString()) // 토큰 제목(주체): userId
                .claim("role", user.getRole().name()) // 사용자 역할 (클레임)
                .claim("name", user.getUsername()) // 사용자 이름 (클레임)
                .claim("userId", user.getId()) // 사용자 ID (클레임)
                .setIssuedAt(new Date(now))
                .setExpiration(validity) // 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 💡 --------------------- 추가된 메서드 --------------------- 💡

    /**
     * 유효한 토큰을 기반으로 인증 정보(Authentication) 객체를 생성하여 반환합니다.
     */
    public Authentication getAuthentication(String token) {
        // 1. 토큰에서 사용자 ID 추출
        Long userId = extractUserId(token);
        String userIdStr = String.valueOf(userId);

        // 2. UserDetailsService를 사용하여 사용자 정보 로드 (CustomUserDetails 객체 로드)
        // loadUserByUsername 메서드는 사용자 식별자(여기서는 userId)를 기반으로 작동하도록 구현되어 있어야 함
        UserPrincipal userPrincipal = (UserPrincipal)userDetailsService.loadUserByUsername(userIdStr);

        // 3. UserDetails와 권한 정보를 담아 Authentication 객체(인증 완료) 생성 및 반환
        return new UsernamePasswordAuthenticationToken(userPrincipal, "", userPrincipal.getAuthorities());
    }

    /**
     * 토큰의 유효성을 검증하고 문제가 없으면 true를 반환합니다.
     */
    public boolean validateToken(String token) {
        try {
            // 토큰을 파싱하면서 서명 검증 및 만료 시간 검증을 동시에 수행합니다.
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명 또는 구조: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 비어있음: {}", e.getMessage());
        }
        return false;
    }

    // 💡 --------------------- 기존 메서드 --------------------- 💡

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("name", String.class);
    }

    public Long extractUserId(String token) {
        // 토큰이 만료되었더라도 userId 추출은 가능해야 하므로 parseClaimsJws 대신 parseClaims를 사용하여 만료 예외를 무시할 수 있지만,
        // 여기서는 기존 방식을 유지하고 필터에서 validateToken으로 먼저 검사하도록 합니다.
        // *참고: 만료된 토큰에서 클레임을 추출하려면 parseClaimsJws 대신 try-catch 블록 내에서 parseClaims 메서드를 구현해야 합니다.*
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    public long getVaidTime(){
        return this.tokenValidityInMilliseconds;
    }
}