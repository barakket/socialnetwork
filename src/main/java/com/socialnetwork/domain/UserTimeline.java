package com.socialnetwork.domain;

import java.util.List;

/**
 * Created by Marcin.
 */
public class UserTimeline {
    private String userId;

    private List<TimelineMessage> timelineMessages;

    UserTimeline(String userId, List<TimelineMessage> timelineMessages) {
        this.userId = userId;
        this.timelineMessages = timelineMessages;
    }

    public String getUserId() {
        return userId;
    }

    public List<TimelineMessage> getTimelineMessages() {
        return timelineMessages;
    }
}
