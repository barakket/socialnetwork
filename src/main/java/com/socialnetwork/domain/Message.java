package com.socialnetwork.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Marcin.
 */
public class Message {
    private final String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd,HH:mm:ss")
    private final Date created;

    @JsonCreator
    Message(@JsonProperty("content") String content, Date created) {
        this.content = content;
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public Date getCreated() {
        return created;
    }
}
