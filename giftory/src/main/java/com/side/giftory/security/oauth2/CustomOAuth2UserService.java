package com.side.giftory.security.oauth2;

import com.side.giftory.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

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
        UserPrincipal userPrincipal = strategy.extractUser(attributes);


        //프로바이더와 프로바이더 키를 통해서 사용자 id 를 갖고온 후 이를 통해서 사용자 정보 조회

        //사용자 없으면 새롭게 생성

        //사용자 존재 시 이를 넘김

        //가져온 사용자 정보 토대로 userPrincipal에 값 set



        return userPrincipal;
    }
}
