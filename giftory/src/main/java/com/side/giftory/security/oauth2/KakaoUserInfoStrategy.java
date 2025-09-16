package com.side.giftory.security.oauth2;


import com.side.giftory.security.RoleType;
import com.side.giftory.security.UserPrincipal;
import com.side.giftory.user.domain.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Map;

public class KakaoUserInfoStrategy implements OAuthUserInfoStrategy {

    @Override
    public UserPrincipal extractUser(Map<String, Object> attributes) {
//        ( String username, String email ,String passWord, String phoneNo , SocialType socialType , RoleType roleType , Map<String,Object> attributes)
        SocialType socialType = SocialType.KAKAO;
        // 카카오 고유 ID
        String socialId = String.valueOf(attributes.get("id"));

        // kakao_account 하위 데이터
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String name = (String) profile.get("nickname");
        String profileImage = (String) profile.get("profile_image_url");

        return new UserPrincipal(name ,email , "" , "" , socialType ,socialId , null ,attributes);
    }
}