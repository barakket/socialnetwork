package com.socialnetwork.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Marcin.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalActionException extends RuntimeException {

    public IllegalActionException () {
        super("User cannot follow himself!");
    }
}
