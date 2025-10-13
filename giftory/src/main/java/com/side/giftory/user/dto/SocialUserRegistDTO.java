package com.side.giftory.user.dto;

import com.side.giftory.security.oauth2.SocialType;
import lombok.*;

/**
 * 소셜 로그인한 사용자를 임시저장하는 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialUserRegistDTO {
    private String username;

    private String email;

    private String phone;

    private String profileUrl;

    private SocialType socialType;

    private String socialId;
}
