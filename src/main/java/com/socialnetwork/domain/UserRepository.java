package com.socialnetwork.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Marcin.
 */
@Repository
public class UserRepository {

    private static final List<User> users = new ArrayList<>();

    public User createNew(String userId) {
        User user = new User(userId);
        users.add(user);
        return user;
    }

    public Optional<User> findByUserId(String userId) {
        return users
                .stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();
    }

    public void cleanRepository() {
        users.clear();
    }
}
