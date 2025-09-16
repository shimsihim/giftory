package com.side.giftory.user.repository;

import com.side.giftory.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository{
    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findById(long id) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return null;
    }
}
