package com.socialnetwork.domain;

/**
 * Created by Marcin.
 */
public class TimelineMessage {
    private String author;
    private Message message;

    TimelineMessage(String author, Message message) {
        this.author = author;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public Message getMessage() {
        return message;
    }
}
