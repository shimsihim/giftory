package com.side.giftory.user.service;

import com.side.giftory.security.UserPrincipal;
import com.side.giftory.user.domain.User;
import com.side.giftory.user.dto.UserSocialRegistDTO;

public interface UserService {

    User registerSocial(UserPrincipal userPrincipal);
}
