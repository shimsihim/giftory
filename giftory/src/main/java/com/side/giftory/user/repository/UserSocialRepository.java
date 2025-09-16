package com.side.giftory.user.repository;

import com.side.giftory.user.domain.UserSocial;

import java.util.Optional;

public interface UserSocialRepository {
    UserSocial save(UserSocial social);
}
