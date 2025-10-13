package com.side.giftory.user.service;

import com.side.giftory.common.exception.UserNotFoundException;
import com.side.giftory.security.RoleType;
import com.side.giftory.security.UserPrincipal;
import com.side.giftory.user.domain.User;
import com.side.giftory.user.domain.UserSocial;
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
public class UserSocialServiceImpl implements UserSocialService{

    private final UserSocialRepository userSocialRepository;

    public UserSocialServiceImpl(UserSocialRepository userSocialRepository) {
        this.userSocialRepository = userSocialRepository;
    }

    /**
     * 소셜 로그인 정보 저장
     *
     * @param user : 임시 저장된 사용자 정보
     * @param userPrincipal : 로그인 시 인증 및 인가에 사용되는 사용자 정보
     * @return 생성된 UserSocial
     */
    public UserSocial registerSocial(User user , UserPrincipal userPrincipal) {
        UserSocial userSocial = UserSocial.builder()
                        .user(user)
                        .socialId(userPrincipal.getSocialId())
                        .socialType(userPrincipal.getSocialType())
                        .build();
        UserSocial savedUserSocial = userSocialRepository.save(userSocial);

        return savedUserSocial;
    }

    @Override
    @Transactional(readOnly = true)
    public UserSocial findSocial(UserPrincipal userPrincipal) {
                return userSocialRepository
                                .findBySocialTypeAndSocialIdWithUser(userPrincipal.getSocialType(), userPrincipal.getSocialId())
                                .orElseThrow(() -> new UserNotFoundException());
            }
}
