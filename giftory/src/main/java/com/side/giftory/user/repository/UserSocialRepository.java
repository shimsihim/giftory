package com.side.giftory.user.repository;

import com.side.giftory.security.oauth2.SocialType;
import com.side.giftory.user.domain.UserSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserSocialRepository extends JpaRepository<UserSocial, Long> {
    UserSocial save(UserSocial social);

    @Query("SELECT us FROM UserSocial us JOIN FETCH us.user WHERE us.socialType = :socialType AND us.socialId = :socialId")
    Optional<UserSocial> findBySocialTypeAndSocialIdWithUser(
            @Param("socialType") SocialType socialType,
            @Param("socialId") String socialId);
}
