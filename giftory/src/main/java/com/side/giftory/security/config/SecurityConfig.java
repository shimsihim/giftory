package com.side.giftory.security.config;

import com.side.giftory.security.RoleType;
import com.side.giftory.security.UserPrincipal;
import com.side.giftory.security.oauth2.CustomOAuth2UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http , CustomOAuth2UserService customOAuth2UserService) throws Exception {
        http
//                .csrf(csrf -> csrf.disable())
                .csrf(csrf -> csrf.csrfTokenRepository(org.springframework.security.web.csrf.CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/error/**", "/login/**","/oauth2/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))  // 명시적 연결
                        .successHandler((request, response, authentication) -> {
                            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
                            RoleType roleType = principal.getRoleType();
                            String redirectUri = roleType == RoleType.ROLE_GUEST ? "/signup" : "/";
                            log.info("==== 로그인 성공");
                            response.sendRedirect(redirectUri); // 로그인 성공 후 홈
                        })
                        .failureHandler((request, response, exception) -> {
                            log.error("==== OAuth2 로그인 실패: {}", exception.getMessage());
                            response.sendRedirect("/login");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
