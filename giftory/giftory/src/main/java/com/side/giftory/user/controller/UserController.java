package com.side.giftory.user.controller;

import com.side.giftory.security.UserPrincipal;
import com.side.giftory.user.UserMapper;
import com.side.giftory.user.dto.request.RegistUserDTO;
import com.side.giftory.user.dto.response.UserDTO;
import com.side.giftory.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @PostMapping("signup")
    public void signup(@RequestBody @Valid RegistUserDTO registUserDTO , @AuthenticationPrincipal UserPrincipal userPrincipal) {

        log.info("userPrincipal = {}", userPrincipal);
        userService.signup(userPrincipal , registUserDTO);
    }

    @GetMapping("")
    public UserDTO getUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal){
        log.debug("getUserInfo: userPrincipal={}", userPrincipal);
        return userMapper.toDTO(userPrincipal);
    }

    @PostMapping("/update")
    public void updateInfo(@RequestBody @Valid RegistUserDTO registUserDTO , @AuthenticationPrincipal UserPrincipal userPrincipal) {

        log.info("userPrincipal = {}", userPrincipal);
        userService.updateUserInfo(userPrincipal , registUserDTO);
    }

    @DeleteMapping("/delete")
    public void updateInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        userService.deleteUser(userPrincipal);
    }


}
