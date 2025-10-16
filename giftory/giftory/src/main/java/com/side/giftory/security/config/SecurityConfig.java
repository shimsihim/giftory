package com.side.giftory.security.config;

import com.side.giftory.security.RoleType;
import com.side.giftory.security.UserPrincipal;
import com.side.giftory.security.jwt.JwtAuthenticationFilter;
import com.side.giftory.security.jwt.JwtTokenProvider;
import com.side.giftory.security.oauth2.CustomOAuth2UserService;
import com.side.giftory.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Duration;

@Configuration
@Slf4j
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 설정 파일 (application.yml 또는 properties)에서 값을 주입받는 필드
    private final String frontEndUrl;

    // 💡 생성자 주입을 통해 의존성 주입과 설정 값 주입을 동시에 처리합니다.
    public SecurityConfig(
            JwtTokenProvider jwtTokenProvider,
            UserMapper userMapper,
            @Value("${app.frontend.url}") String frontEndUrl, // 설정 파일에서 'app.frontend.url' 값을 주입
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userMapper = userMapper;
        this.frontEndUrl = frontEndUrl;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http , CustomOAuth2UserService customOAuth2UserService) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // 2. REST API이므로 세션 STATELESS 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/get-userinfo", "/api/user/signup","/error/**", "/login/**","/oauth2/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))  // 명시적 연결
                        .successHandler((request, response, authentication) -> {
                            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

                            String jwtToken = jwtTokenProvider.createToken(userMapper.toEntity(principal));
                            RoleType roleType = principal.getRoleType();
                            String redirectUrl;
                            // 3. 역할(Role)에 따라 다음 이동 경로 결정
                            if (RoleType.ROLE_GUEST.equals(roleType)) {
                                redirectUrl = frontEndUrl + "/regist"; // 회원가입 페이지
                            } else {
                                redirectUrl = frontEndUrl + "/"; // 메인 페이지
                            }
                            // 2. JWT를 HttpOnly 쿠키에 설정
                            ResponseCookie cookie = ResponseCookie.from("accessToken", jwtToken)
                                    .httpOnly(true) // JavaScript 접근 금지
                                    .secure(true)   // HTTPS 환경에서만 전송
                                    .path("/")
                                    .sameSite("Strict") // CSRF 공격 방어
                                    .maxAge(Duration.ofMillis(jwtTokenProvider.getVaidTime()))
                                    .build();
                            log.warn("redirect to {}", redirectUrl);

                            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//                            // 💡 HTTP 302 응답을 내려서 브라우저에게 최종 목적지로 이동하라고 명령합니다.
                            response.sendRedirect(redirectUrl);
                        })
                        .failureHandler((request, response, exception) -> {
                            log.error("==== OAuth2 로그인 실패: {}", exception.getMessage());
                            //response.sendRedirect("/login");
                        })
                        // 기본 로그인 시작 URI 변경
                        .authorizationEndpoint(endpoint ->
                                endpoint.baseUri("/api/oauth2/authorization")
                        )
                        // 로그인 성공 후 리다이렉션 URI 설정
                        .redirectionEndpoint(endpoint ->
                                endpoint.baseUri("/api/login/oauth2/code/*") // 기본값: /login/oauth2/code/*
                        )
                )
                // 4. JWT 필터를 UsernamePasswordAuthenticationFilter 이전에 등록
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
