package com.side.giftory.security.oauth2;

import com.side.giftory.security.UserPrincipal;
import com.side.giftory.user.domain.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public interface OAuthUserInfoStrategy {
    UserPrincipal extractUser(Map<String, Object> attributes);
}