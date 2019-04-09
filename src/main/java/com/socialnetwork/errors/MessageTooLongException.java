package com.socialnetwork.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Marcin.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MessageTooLongException extends RuntimeException {

    public MessageTooLongException(long size) {
        super("Max number of chars in post is 140. You had " + size);
    }
}
