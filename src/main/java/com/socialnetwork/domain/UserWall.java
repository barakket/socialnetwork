package com.socialnetwork.domain;

import java.util.List;

/**
 * Created by Marcin.
 */
public class UserWall {
    private String userId;
    private List<Message> wallMessages;

    UserWall(String userId, List<Message> wallMessages) {
        this.userId = userId;
        this.wallMessages = wallMessages;
    }

    public String getUserId() {
        return userId;
    }

    public List<Message> getWallMessages() {
        return wallMessages;
    }
}
