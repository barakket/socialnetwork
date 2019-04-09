package com.socialnetwork.domain;

import com.socialnetwork.errors.MessageTooLongException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by Marcin.
 */
@Service
public class PostingService {

    private static final int MAX_POST_LENGTH = 140;

    private UserRepository userRepository;

    @Autowired
    public PostingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageResource post(String userId, String text) {
        validateMessageLength(text);

        User user = userRepository.findByUserId(userId)
                .orElse(userRepository.createNew(userId));

        Message msg = new Message(text, Calendar.getInstance().getTime());
        user.addMessage(msg);
        return new MessageResource(msg, user.getUserId());
    }

    private void validateMessageLength(String content) {
        if (content.length() > MAX_POST_LENGTH) {
            throw new MessageTooLongException(content.length());
        }
    }
}
