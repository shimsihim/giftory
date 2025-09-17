package com.side.giftory.user.service;

import com.side.giftory.security.RoleType;
import com.side.giftory.security.UserPrincipal;
import com.side.giftory.security.oauth2.SocialType;
import com.side.giftory.user.domain.User;
import com.side.giftory.user.domain.UserSocial;
import com.side.giftory.user.dto.UserSocialRegistDTO;
import com.side.giftory.user.repository.UserRepository;
import com.side.giftory.user.repository.UserSocialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * UserService
 *
 * User 도메인의 CRUD, 일반회원가입, 소셜 임시회원 생성, 임시 → 정식회원 전환 관리
 *
 *  - User 엔티티와 User-Social 매핑을 모두 관리
 *  - Role 변경, Soft Delete 처리 포함
 */
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserSocialRepository userSocialRepository;

    public UserServiceImpl(UserRepository userRepository, UserSocialRepository userSocialRepository) {
        this.userRepository = userRepository;
        this.userSocialRepository = userSocialRepository;
    }

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

        UserSocial social = UserSocial.builder()
                            .socialId(userPrincipal.getSocialId())
                            .socialType(userPrincipal.getSocialType())
                            .user(user)
                            .build();
        userSocialRepository.save(social);

        return savedUser;
    }

    /**
     * 임시회원(GUEST) → 정식회원(USER) 전환
     *
     * @param userId 임시회원 User ID
     * @param username 입력된 사용자 이름
     * @return 업데이트된 User
     * @throws NoSuchElementException 존재하지 않는 User ID
     */
    public User convertGuestToUser(Long userId, String username) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자 없음"));
        user.setUsername(username);
        user.setRole(RoleType.ROLE_USER);
        return userRepository.save(user);
    }

    // Soft Delete
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("삭제할 사용자가 없습니다."));
        user.delete();
        userRepository.save(user);
    }

    public void restoreUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.restore();
        userRepository.save(user);
    }
}
