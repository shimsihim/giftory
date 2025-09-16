package com.side.giftory.security.oauth2;

import org.springframework.stereotype.Component;

@Component
public class OAuthUserInfoFactory {

    public OAuthUserInfoStrategy getStrategy(String registrationId) {
        switch (registrationId.toLowerCase()) {
            case "kakao":
                return new KakaoUserInfoStrategy();
            default:
                throw new IllegalArgumentException("지원하지 않는 소셜 로그인: " + registrationId);
        }
    }
}