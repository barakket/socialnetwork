package com.socialnetwork;

import com.socialnetwork.errors.UserNotFoundException;
import com.socialnetwork.domain.FollowingService;
import com.socialnetwork.domain.MessageResource;
import com.socialnetwork.domain.PostingService;
import com.socialnetwork.domain.TimelineService;
import com.socialnetwork.domain.UserTimelineResource;
import com.socialnetwork.domain.UserWall;
import com.socialnetwork.domain.WallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Marcin.
 */
@RestController
public class SocialNetworkRestController {

    private PostingService postingService;
    private FollowingService followingService;
    private TimelineService timelineService;
    private WallService wallService;

    @Autowired
    SocialNetworkRestController(PostingService postingService,
                                FollowingService followingService,
                                TimelineService timelineService,
                                WallService wallService) {
        this.postingService = postingService;
        this.followingService = followingService;
        this.timelineService = timelineService;
        this.wallService = wallService;
    }

    @RequestMapping(path = "/{userId}/post", method = RequestMethod.POST)
    public HttpEntity<MessageResource> post(@PathVariable String userId, @RequestBody String content) {
        MessageResource message = postingService.post(userId, content);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    @RequestMapping(path = "/{userId}/wall", method = RequestMethod.GET)
    public HttpEntity<UserWall> wall(@PathVariable String userId) throws UserNotFoundException {
        UserWall userWall = wallService.createUserWall(userId);

        return new ResponseEntity<>(userWall, HttpStatus.OK);
    }

    @RequestMapping(path = "/{userId}/follow/{followeeId}", method = RequestMethod.PUT)
    public Object follow(@PathVariable String userId, @PathVariable String followeeId) {
        boolean created = followingService.follow(userId, followeeId);

        return new ResponseEntity<>(created ? HttpStatus.CREATED : HttpStatus.OK);
    }


    @RequestMapping(path = "/{userId}/timeline", method = RequestMethod.GET)
    public HttpEntity<UserTimelineResource> timeline(@PathVariable String userId) {
        UserTimelineResource userTimeline = timelineService.createUserTimeline(userId);

        return new ResponseEntity<>(userTimeline, HttpStatus.OK);
    }

}
