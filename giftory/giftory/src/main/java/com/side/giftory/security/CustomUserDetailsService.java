package com.side.giftory.security;

import com.side.giftory.user.domain.User;
import com.side.giftory.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // UserRepository를 주입받아 DB 조회를 수행합니다.
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * JWT에서 추출된 사용자 ID(String 형태)를 받아 UserPrincipal 객체를 로드합니다.
     * @param userIdStr JWT의 Subject 또는 Claim에서 넘어온 사용자 ID (String 형태)
     * @return UserDetails를 구현한 UserPrincipal 객체
     * @throws UsernameNotFoundException 사용자를 찾을 수 없을 때 발생
     */
    @Override
    public UserDetails loadUserByUsername(String userIdStr) throws UsernameNotFoundException {

        // 1. String 형태의 사용자 ID를 Long 타입으로 변환
        Long userId;
        try {
            userId = Long.valueOf(userIdStr);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("유효하지 않은 사용자 ID 형식: " + userIdStr);
        }

        // 2. UserRepository를 사용하여 DB에서 사용자 엔티티 조회
        // 이 과정에서 User 엔티티가 UserSocial 엔티티와 함께 fetchJoin 되어 소셜 정보도 가져오면 좋습니다.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("ID: " + userIdStr + "에 해당하는 사용자를 찾을 수 없습니다."));

        // 3. User 엔티티를 기반으로 UserPrincipal 객체 생성 및 반환

        // 3-1. DB에서 조회된 User 정보를 바탕으로 UserPrincipal의 기본 필드를 채웁니다.
        UserPrincipal userPrincipal = new UserPrincipal(
                user.getUsername(),
                user.getEmail(),
                user.getLoginId(),
                user.getPassword(), // 해시된 비밀번호 (JWT 인증에서는 사용되지 않지만 UserDetails 요구사항)
                user.getPhoneNo(),
                // 소셜 로그인으로 생성된 계정이라면, DB에서 소셜 타입/ID를 가져와야 합니다.
                // User 엔티티에 Social 정보를 직접 저장하거나, 별도의 repository 조회가 필요합니다.
                // 여기서는 예시로 null, "" 처리합니다. (일반 로그인/JWT 재인증 경로로 가정)
                null,
                "",
                user.getRole(),
                Collections.emptyMap() // JWT 재인증 시 attributes는 필요 없습니다.
        );

        // 3-2. 추가적으로 User 엔티티의 ID, 역할 등을 UserPrincipal에 설정 (setByUser 활용)
        userPrincipal.setByUser(user);

        // 3-3. ID 필드가 private final이 아니므로, UserPrincipal 내부의 id 필드를 설정해야 합니다.
        // UserPrincipal 클래스에 public setter가 없다면, 생성자나 setByUser를 통해 설정합니다.
        // 현재 setByUser에 id 설정 로직이 있으므로 이를 활용합니다.

        return userPrincipal;
    }
}