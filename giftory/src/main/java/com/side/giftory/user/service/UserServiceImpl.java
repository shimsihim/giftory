package com.side.giftory.user.service;

import com.side.giftory.common.exception.UserNotFoundException;
import com.side.giftory.security.RoleType;
import com.side.giftory.security.UserPrincipal;
import com.side.giftory.user.UserMapper;
import com.side.giftory.user.domain.User;
import com.side.giftory.user.dto.request.RegistUserDTO;
import com.side.giftory.user.dto.response.UserDTO;
import com.side.giftory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * UserService
 *
 * User 도메인의 CRUD, 일반회원가입, 소셜 임시회원 생성, 임시 → 정식회원 전환 관리
 *
 *  - User 엔티티와 User-Social 매핑을 모두 관리
 *  - Role 변경, Soft Delete 처리 포함
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserSocialService userSocialService;
    private final UserMapper userMapper;

    /**
     * 일반 회원가입 처리
     *
     * @param dto 이메일, 비밀번호 정보
     * @return 생성된 User
     * @throws IllegalArgumentException 이메일 중복 시
     */
/*    public User registerNormal(User dto) {
        Optional<User> exist = userRepository.findByEmail(dto.getEmail());
        if (exist.isPresent()) throw new IllegalArgumentException("이미 존재하는 이메일");
        dto.setRole(RoleType.ROLE_USER);
        return userRepository.save(dto);
    }*/

    /**
     * 소셜 로그인 임시회원 생성
     *
     * @param dto 소셜 로그인 정보
     * @return 생성된 User
     */
    @Transactional
    public User registerSocial(UserPrincipal userPrincipal) {
        User user = User.builder()
                .username(userPrincipal.getUsername() != null ? userPrincipal.getUsername() : userPrincipal.getSocialType()+"_guest")
                        .email(userPrincipal.getEmail())
                        .phoneNo(userPrincipal.getPhoneNo())
                        .role(RoleType.ROLE_GUEST)
                        .build();
        User savedUser = userRepository.save(user);

        userSocialService.registerSocial(savedUser , userPrincipal);

        return savedUser;
    }

    @Override//회원가입
    public UserDTO signup(UserPrincipal userPrincipal, RegistUserDTO registUserDTO) {
        User registUser;
        if(userPrincipal == null){
            registUser = new User();
            registUser.registUser(registUserDTO);
            registUser = userRepository.save(registUser);
        }
        else{
            registUser = userRepository.findById(userPrincipal.getId())
                    .map(user -> {
                        user.registUser(registUserDTO);
                        return user;
                    })
                    .orElseThrow(() -> new UserNotFoundException());
        }
        return userMapper.toDTO(registUser);
    }

    @Override
    public UserDTO updateUserInfo(UserPrincipal userPrincipal, RegistUserDTO registUserDTO) {
        User updateUserInfo = userRepository.findById(userPrincipal.getId())
                .map(user -> {
                    user.updateUserInfo(registUserDTO);
                    return user;
                })
                .orElseThrow(() -> new UserNotFoundException());
        return userMapper.toDTO(updateUserInfo);
    }

    @Override
    public UserDTO deleteUser(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(()-> new NoSuchElementException("삭제할 사용자가 없습니다."));
        user.delete();
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public void restoreUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.restore();
        userRepository.save(user);
    }
}
