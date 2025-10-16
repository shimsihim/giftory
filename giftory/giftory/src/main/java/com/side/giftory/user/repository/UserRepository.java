package com.side.giftory.user.repository;

import com.side.giftory.user.domain.User;
import com.side.giftory.user.domain.UserSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    Optional<User> findById(long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByLoginId(String loginId);
}
