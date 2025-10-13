package com.side.giftory.user.dto;

import com.side.giftory.security.oauth2.SocialType;
import com.side.giftory.user.domain.User;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;


/**
 * 처음 소셜 가입 시 소셜 정보 등록을 위한 DTO
 */
@Data
@Builder
public class UserSocialRegistDTO {
    private User user;
    private SocialType socialType;
    private String socialId;
}
