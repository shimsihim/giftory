package com.side.giftory.security.oauth2;


import com.side.giftory.security.RoleType;
import com.side.giftory.security.UserPrincipal;
import com.side.giftory.user.domain.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KakaoUserInfoStrategy implements OAuthUserInfoStrategy {

    @Override
    public UserPrincipal extractUser(Map<String, Object> attributes) {
//        ( String username, String email ,String passWord, String phoneNo , SocialType socialType , RoleType roleType , Map<String,Object> attributes)
        SocialType socialType = SocialType.KAKAO;
        // 카카오 고유 ID
        Object idObj = attributes.get("id");
        if (idObj == null) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_user_info"),
                    "Kakao user info: 'id' is missing");
        }
        String socialId = String.valueOf(idObj);

        // kakao_account 하위 데이터
        Map<String, Object> kakaoAccount = OAuth2UserInfoUtils.safeGetMap(attributes.get("kakao_account"));
        Map<String, Object> profile = OAuth2UserInfoUtils.safeGetMap(kakaoAccount.get("profile"));

        String email = (String) kakaoAccount.get("email"); // null 가능
        String name = OAuth2UserInfoUtils.safeGetString(profile, "nickname");
        name = getOrDefaultName(name , socialId);
        String profileImage = OAuth2UserInfoUtils.safeGetString(profile, "profile_image_url");

        return new UserPrincipal(name ,email , "" ,"", "" , socialType ,socialId , null ,attributes);
    }

    @Override
    public String getProviderId() {
        return "kakao";
    }
}