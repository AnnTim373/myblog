package ru.practicum.myblog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.myblog.TestContext;
import ru.practicum.myblog.service.PostService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LikeControllerTest extends TestContext {

    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @InjectMocks
    private LikeController likeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();
    }

    @Test
    void testAddReaction_Like() throws Exception {
        Long postId = 1L;
        boolean like = true;

        mockMvc.perform(post("/posts/{postId}/like?like={like}", postId, like))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + postId));
    }

    @Test
    void testAddReaction_Dislike() throws Exception {
        Long postId = 1L;
        boolean like = false;

        mockMvc.perform(post("/posts/{postId}/like?like={like}", postId, like))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + postId));
    }

    @Test
    void testAddReaction_Error() throws Exception {
        Long postId = 1L;
        boolean like = true;

        doThrow(new RuntimeException("Error while processing like")).when(postService).editLikesCount(anyLong(), anyBoolean());

        mockMvc.perform(post("/posts/{postId}/like?like={like}", postId, like))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("error"));
    }

}
