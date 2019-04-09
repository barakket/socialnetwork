package com.socialnetwork.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Marcin.
 */
public class User {
    private final String userId;
    private List<Message> messages;
    private Set<User> followees;

    User(String userId) {
        this.userId = userId;
        this.messages = new ArrayList<>();
        this.followees = new HashSet<>();
    }

    public String getUserId() {
        return userId;
    }

    Set<User> getFollowees() {
        return followees;
    }

    List<Message> getMessages() {
        return messages;
    }

    void addMessage(Message message) {
        messages.add(message);
    }

    boolean addFolowee(User followee) {
        return followees.add(followee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
