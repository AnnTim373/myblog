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
import ru.practicum.myblog.domain.Comment;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.CommentDTO;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.dto.PostView;
import ru.practicum.myblog.repository.CommentRepository;
import ru.practicum.myblog.service.PostService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import({TestContext.class})
@AutoConfigureMockMvc
public class CommentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @MockitoSpyBean(reset = MockReset.BEFORE)
    private CommentRepository commentRepository;

    @Autowired
    private CommentController commentController;

    private final PostDTO postDTO = PostDTO.builder()
            .content("Comment Test Content")
            .title("Comment Test Title")
            .tags("#testTag1 #testTag2 #comment")
            .build();

    @Test
    void testSaveNewCommentWithSuccess() throws Exception {
        Post saved = postService.save(postDTO);
        mockMvc.perform(post("/posts/{postId}/comments", saved.getId())
                        .param("content", "New Comment Content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + saved.getId()));
    }

    @Test
    void testEditCommentWithSuccess() throws Exception {
        Post savedPost = postService.save(postDTO);
        Comment savedComment = commentRepository.save(
                Comment.builder()
                        .postId(savedPost.getId())
                        .content("Test Comment Content")
                        .build()
        );
        mockMvc.perform(post("/posts/{postId}/comments/{commentId}", savedPost.getId(), savedComment.getId())
                        .param("content", "Another Comment Content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + savedPost.getId()));
        savedComment.setContent("Another Comment Content");
        assertFalse(commentRepository.findByPostId(savedPost.getId()).isEmpty());
        assertEquals(commentRepository.findByPostId(savedPost.getId()).getFirst(), savedComment);
    }

    @Test
    void testDeleteCommentWithSuccess() throws Exception {
        Post savedPost = postService.save(postDTO);
        Comment savedComment = commentRepository.save(
                Comment.builder()
                        .postId(savedPost.getId())
                        .content("Test Comment Content")
                        .build()
        );

        mockMvc.perform(post("/posts/{postId}/comments/{commentId}/delete",
                        savedPost.getId(), savedComment.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/" + savedPost.getId()));

        assertTrue(commentRepository.findByPostId(savedPost.getId()).isEmpty());
    }

    @Test
    void testDeleteCommentWithError() {
        Post savedPost = postService.save(postDTO);
        Comment savedComment = commentRepository.save(
                Comment.builder()
                        .postId(savedPost.getId())
                        .content("Test Comment Content")
                        .build()
        );

        doThrow(new RuntimeException("Error while deleting comment")).when(commentRepository).deleteById(any());

        Model model = new BindingAwareModelMap();
        model.addAttribute("post",
                PostView.builder()
                        .id(savedPost.getId())
                        .title(savedPost.getTitle())
                        .build()
        );

        String view = commentController.deleteComment(savedPost.getId(), savedComment.getId(), model);

        assertEquals("post", view);
        assertEquals("Error while deleting comment", model.getAttribute("error"));
        assertNotNull(model.getAttribute("post"));
    }

    @Test
    void testSaveNewCommentWithError() {
        Post saved = postService.save(postDTO);

        Model model = new BindingAwareModelMap();
        model.addAttribute("post",
                PostView.builder()
                        .id(saved.getId())
                        .title(saved.getTitle())
                        .build()
        );

        doThrow(new RuntimeException("Error while saving comment")).when(commentRepository).save(any());

        String view = commentController.saveComment(
                saved.getId(), null, CommentDTO.builder().content("New Test Comment Content").build(), model
        );

        assertEquals("post", view);
        assertEquals("Error while saving comment", model.getAttribute("error"));
        assertNotNull(model.getAttribute("post"));
    }

    @Test
    void testEditCommentWithError() {
        Post savedPost = postService.save(postDTO);
        Comment savedComment = commentRepository.save(
                Comment.builder()
                        .postId(savedPost.getId())
                        .content("Test Comment Content")
                        .build()
        );

        Model model = new BindingAwareModelMap();
        model.addAttribute("post",
                PostView.builder()
                        .id(savedPost.getId())
                        .title(savedPost.getTitle())
                        .build()
        );

        doThrow(new RuntimeException("Error while editing comment")).when(commentRepository).save(any());

        String view = commentController.saveComment(
                savedPost.getId(), savedComment.getId(),
                CommentDTO.builder().content("New Test Comment Content").build(), model
        );

        assertEquals("post", view);
        assertEquals("Error while editing comment", model.getAttribute("error"));
        assertNotNull(model.getAttribute("post"));
    }

}
