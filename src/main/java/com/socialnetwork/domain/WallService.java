package com.socialnetwork.domain;

import com.socialnetwork.errors.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Marcin.
 */
@Service
public class WallService {

    private UserRepository userRepository;

    @Autowired
    public WallService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public UserWall createUserWall(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<Message> wallMessages = user.getMessages();
        wallMessages.sort((m1, m2) -> m2.getCreated().compareTo(m1.getCreated()));

        return new UserWall(userId, wallMessages);
    }
}
