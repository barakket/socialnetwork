package com.socialnetwork.domain;

import com.socialnetwork.errors.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin.
 */
@Service
public class TimelineService {

    private UserRepository userRepository;

    @Autowired
    public TimelineService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserTimelineResource createUserTimeline(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        UserTimeline userTimeline = new UserTimeline(userId, createTimelineMesssages(user));
        return new UserTimelineResource(userTimeline);
    }

    private List<TimelineMessage> createTimelineMesssages(User user) {
        List<TimelineMessage> timelineMessages = new ArrayList<>();
        user.getFollowees()
                .stream()
                .forEach(u -> extractMessagesFromFolowee(u, timelineMessages));
        timelineMessages.sort((tm1, tm2) -> tm2.getMessage().getCreated().compareTo(tm1.getMessage().getCreated()));
        return timelineMessages;
    }

    private void extractMessagesFromFolowee(User followee, List<TimelineMessage> timelineMessages) {
        followee.getMessages()
                .stream()
                .forEach(
                        m -> timelineMessages.add(new TimelineMessage(followee.getUserId(), m))
                );
    }
}
