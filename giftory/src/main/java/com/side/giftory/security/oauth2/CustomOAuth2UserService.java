package com.side.giftory.security.oauth2;

import com.side.giftory.security.UserPrincipal;
import com.side.giftory.user.domain.User;
import com.side.giftory.user.repository.UserSocialRepository;
import com.side.giftory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.side.giftory.user.domain.UserSocial;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private static UserSocialRepository userSocialRepository;
    @Autowired
    private static UserService userService;


    private final OAuthUserInfoFactory oAuthUserInfoFactory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // "kakao"
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthUserInfoStrategy strategy = oAuthUserInfoFactory.getStrategy(registrationId);
        if (strategy == null) {
            throw new OAuth2AuthenticationException("Unsupported OAuth2 provider: " + registrationId);
        }

        UserPrincipal userPrincipal = strategy.extractUser(attributes); // name과 이메일 정도 , 타입 , 소셜아이디...

        User user = userSocialRepository.findBySocialTypeAndSocialIdWithUser(userPrincipal.getSocialType(), registrationId)
                .map(UserSocial::getUser)
                .orElseGet(() -> userService.registerSocial(userPrincipal));

        userPrincipal.setByUser(user);
        return userPrincipal;
    }
}
