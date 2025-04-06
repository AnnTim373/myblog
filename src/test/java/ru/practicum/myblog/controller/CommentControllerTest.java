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
import ru.practicum.myblog.dto.CommentDTO;
import ru.practicum.myblog.service.CommentService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest extends TestContext {

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void testSaveComment_Success() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .content("Test comment").build();

        mockMvc.perform(post("/posts/1/comments")
                        .flashAttr("comment", commentDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    void testSaveComment_Error() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .content("Test comment").build();

        doThrow(new RuntimeException("Error")).when(commentService).save(anyLong(), anyLong(), any(CommentDTO.class));

        mockMvc.perform(post("/posts/1/comments")
                        .flashAttr("comment", commentDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testDeleteComment_Success() throws Exception {
        mockMvc.perform(post("/posts/1/comments/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    void testDeleteComment_Error() throws Exception {
        doThrow(new RuntimeException("Error")).when(commentService).deleteById(anyLong());

        mockMvc.perform(post("/posts/1/comments/1/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("error"));
    }

}
