package com.socialnetwork.domain;

import com.socialnetwork.errors.IllegalActionException;
import com.socialnetwork.errors.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Marcin.
 */
@Service
public class FollowingService {

    private UserRepository userRepository;

    @Autowired
    public FollowingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean follow(String followerId, String followeeId) {
        validateIfNotSameUser(followerId, followeeId);

        User follower = findUserOrThrowException(followerId);
        User followee = findUserOrThrowException(followeeId);

        return follower.addFolowee(followee);
    }


    private User findUserOrThrowException(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private void validateIfNotSameUser(String userId, String followingId) {
        if (userId.equals(followingId)) {
            throw new IllegalActionException();
        }
    }
}
