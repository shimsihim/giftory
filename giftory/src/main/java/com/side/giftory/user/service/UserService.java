package com.side.giftory.user.service;

import com.side.giftory.security.UserPrincipal;
import com.side.giftory.user.domain.User;
import com.side.giftory.user.dto.request.RegistUserDTO;
import com.side.giftory.user.dto.response.UserDTO;

public interface UserService {

    User registerSocial(UserPrincipal userPrincipal);

    UserDTO signup(UserPrincipal userPrincipal , RegistUserDTO registUserDTO);

    UserDTO updateUserInfo(UserPrincipal userPrincipal, RegistUserDTO registUserDTO);

    UserDTO deleteUser(UserPrincipal userPrincipal);
}
