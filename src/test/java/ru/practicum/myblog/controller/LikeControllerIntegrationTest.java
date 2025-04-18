package ru.practicum.myblog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockReset;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import ru.practicum.myblog.TestContext;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.dto.PostView;
import ru.practicum.myblog.repository.PostRepository;
import ru.practicum.myblog.service.PostService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import({TestContext.class})
@AutoConfigureMockMvc
public class LikeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private LikeController likeController;

    @MockitoSpyBean(reset = MockReset.AFTER)
    private PostRepository postRepository;

    private final PostDTO postDTO = PostDTO.builder()
            .content("Comment Test Content")
            .title("Comment Test Title")
            .tags("#testTag1 #testTag2 #comment")
            .build();

    @Test
    void addNegativeSingleReactionWithSuccess() throws Exception {

        Post saved = postService.save(postDTO);

        mockMvc.perform(post("/posts/{postId}/like", saved.getId()).param("like", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + saved.getId()));

        assertTrue(postRepository.findById(saved.getId()).isPresent());
        assertEquals(-1, (long) postRepository.findById(saved.getId()).get().getLikesCount());
    }

    @Test
    void addPositiveSingleReactionWithSuccess() throws Exception {

        Post saved = postService.save(postDTO);

        mockMvc.perform(post("/posts/{postId}/like", saved.getId()).param("like", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + saved.getId()));

        assertTrue(postRepository.findById(saved.getId()).isPresent());
        assertEquals(1, (long) postRepository.findById(saved.getId()).get().getLikesCount());
    }

    @Test
    void addPositiveDoubleReactionWithSuccess() throws Exception {

        Post saved = postService.save(postDTO);

        mockMvc.perform(post("/posts/{postId}/like", saved.getId()).param("like", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + saved.getId()));

        assertEquals(1, (long) postRepository.findById(saved.getId()).get().getLikesCount());

        mockMvc.perform(post("/posts/{postId}/like", saved.getId()).param("like", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + saved.getId()));

        assertTrue(postRepository.findById(saved.getId()).isPresent());
        assertEquals(2, (long) postRepository.findById(saved.getId()).get().getLikesCount());
    }

    @Test
    void addPositiveFirstReactionWithError() {
        Post saved = postService.save(postDTO);

        Model model = new BindingAwareModelMap();
        model.addAttribute("post",
                PostView.builder()
                        .id(saved.getId())
                        .title(saved.getTitle())
                        .build()
        );

        doThrow(new RuntimeException("Error while saving reaction on post"))
                .when(postRepository).editLikeCount(any(), anyInt());

        String view = likeController.addReaction(saved.getId(), true, model);

        assertEquals("post", view);
        assertEquals("Error while saving reaction on post", model.getAttribute("error"));
        assertNotNull(model.getAttribute("post"));
    }

}
