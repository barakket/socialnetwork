package com.socialnetwork;

import com.socialnetwork.domain.FollowingService;
import com.socialnetwork.domain.PostingService;
import com.socialnetwork.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Marcin.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SocialNetworkApplication.class)
@WebAppConfiguration
public class SocialNetworkRestControllerTest {

    private MediaType contentType = new MediaType("application", "json", Charset.forName("UTF-8"));

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostingService postingService;

    @Autowired
    private FollowingService followingService;


    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        userRepository.cleanRepository();
        postingService.post("oliwia", "First message from Oliwia");
    }

    @Test
    public void postTooLongMessage() throws Exception {
        String userId = "oliwia";

        mockMvc.perform(post("/" + userId + "/post")
                .content("Test message from Oliwia. But this message is too long. We need to add bit more text ... " +
                        " and bit more again. Maybe some more text and just to bee sure we'll add one more line. " +
                        " Yes now it's too long :) ")
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postWithNonExistingUser() throws Exception {
        String userId = "janek";

        mockMvc.perform(post("/" + userId + "/post")
                .content("Test message from Janek")
                .contentType(contentType))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message.content", is("Test message from Janek")))
                .andExpect(jsonPath("$._links.wall.href", containsString("/" + userId + "/wall")));
    }

    @Test
    public void postWithExistingUser() throws Exception {
        String userId = "oliwia";

        mockMvc.perform(post("/" + userId + "/post")
                .content("Test message from Oliwia")
                .contentType(contentType))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$._links.wall.href", containsString("/" + userId + "/wall")));
    }

    @Test
    public void readUserWall() throws Exception {
        String userId = "oliwia";
        Thread.sleep(500);
        postingService.post(userId, "Second message from Oliwia");

        mockMvc.perform(get("/" + userId + "/wall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wallMessages[0].content",
                        containsString("Second message from Oliwia")))
                .andExpect(jsonPath("$.wallMessages[1].content",
                        containsString("First message from Oliwia")));
    }

    @Test
    public void followOtherUser() throws Exception {
        String userId = "oliwia";
        String followingId = "kamil";
        userRepository.createNew(followingId);

        mockMvc.perform(put("/" + userId + "/follow/" + followingId))
                .andExpect(status().isCreated());
    }

    @Test
    public void followUserAlreadyFollowed() throws Exception {
        String userId = "oliwia";
        String followingId = "kamil";
        userRepository.createNew(followingId);
        followingService.follow(userId, followingId);

        mockMvc.perform(put("/" + userId + "/follow/" + followingId))
                .andExpect(status().isOk());
    }

    @Test
    public void followSelfShouldThrowException() throws Exception {
        String userId = "oliwia";
        mockMvc.perform(put("/" + userId + "/follow/" + userId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkTimeline() throws Exception {
        String userId = "oliwia";
        postingService.post("kamil", "First message from Kamil");
        Thread.sleep(500);
        postingService.post("ewelina", "First message from Ewelina");
        Thread.sleep(500);
        postingService.post("kamil", "Second message from Kamil");
        followingService.follow(userId, "kamil");
        followingService.follow(userId, "ewelina");

        mockMvc.perform(get("/" + userId + "/timeline"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userTimeline.timelineMessages[0].message.content",
                        containsString("Second message from Kamil")))
                .andExpect(jsonPath("$.userTimeline.timelineMessages[1].message.content",
                        containsString("First message from Ewelina")))
                .andExpect(jsonPath("$.userTimeline.timelineMessages[2].message.content",
                        containsString("First message from Kamil")));
    }

}