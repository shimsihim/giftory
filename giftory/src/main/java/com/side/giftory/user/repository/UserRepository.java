package com.side.giftory.user.repository;

import com.side.giftory.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(long id);
    Optional<User> findByEmail(String email);
}
