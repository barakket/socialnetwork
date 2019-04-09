package com.socialnetwork.domain;

import com.socialnetwork.SocialNetworkRestController;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Marcin.
 */
public class MessageResource extends ResourceSupport {

    private final Message message;

    public MessageResource (Message message, String userId) {
        this.message = message;
        this.add(linkTo(methodOn(SocialNetworkRestController.class, userId).wall(userId)).withRel("wall"));
    }

    public Message getMessage() {
        return message;
    }
}
