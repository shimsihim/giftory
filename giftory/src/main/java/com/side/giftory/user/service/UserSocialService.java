package com.side.giftory.user.service;

import com.side.giftory.security.UserPrincipal;
import com.side.giftory.user.domain.User;
import com.side.giftory.user.domain.UserSocial;

public interface UserSocialService {

    UserSocial registerSocial(User user , UserPrincipal userPrincipal);
}
