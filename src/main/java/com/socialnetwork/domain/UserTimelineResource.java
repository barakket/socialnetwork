package com.socialnetwork.domain;

import com.socialnetwork.SocialNetworkRestController;
import org.springframework.hateoas.ResourceSupport;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Marcin.
 */
public class UserTimelineResource extends ResourceSupport {
    private UserTimeline userTimeline;

    public UserTimelineResource(UserTimeline userTimeline) {
        this.userTimeline = userTimeline;
        userTimeline.getTimelineMessages().stream()
                .filter(distinctByAuthor(tm -> tm.getAuthor()))
                .forEach((m) ->
                        this.add(linkTo(methodOn(SocialNetworkRestController.class, m.getAuthor())
                                .wall(m.getAuthor()))
                                .withRel(m.getAuthor() + "-wall"))
                );
    }

    public UserTimeline getUserTimeline() {
        return userTimeline;
    }

    private static <T> Predicate<T> distinctByAuthor(Function<? super T, Object> extractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(extractor.apply(t), Boolean.TRUE) == null;
    }
}
