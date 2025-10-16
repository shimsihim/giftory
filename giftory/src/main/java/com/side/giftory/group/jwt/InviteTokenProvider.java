package com.side.giftory.group.jwt;

import com.side.giftory.common.exception.JWTException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class InviteTokenProvider {

    private final SecretKey key;
    private final long defaultTtlHours;

    public InviteTokenProvider(
            @Value("${app.invite.secret}") String secretBase64,
            @Value("${app.invite.ttl-hours:168}") long defaultTtlHours
    ) {
        this.key = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(secretBase64));
        this.defaultTtlHours = defaultTtlHours;
    }

    public String generate(Long groupId, Duration ttl) {
        Instant now = Instant.now();
        Instant exp = now.plus(ttl == null ? Duration.ofHours(defaultTtlHours) : ttl);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject("group-invite")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .claim("gid", groupId)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long parseAndValidate(String token) {
        try{
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            Claims c = jws.getBody();
            if (!"group-invite".equals(c.getSubject())) {
                throw new IllegalArgumentException("Invalid invite token subject");
            }
            Long groupId = ((Number) c.get("gid")).longValue();
            return groupId;
        }
        catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명 또는 구조: {}", e.getMessage());
            throw new JWTException();
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰: {}", e.getMessage());
            throw new JWTException();
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰: {}", e.getMessage());
            throw new JWTException();
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 비어있음: {}", e.getMessage());
            throw new JWTException();
        }
    }
}