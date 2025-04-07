package ru.practicum.myblog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.myblog.TestContext;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.domain.Tag;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.dto.PostView;
import ru.practicum.myblog.dto.page.Page;
import ru.practicum.myblog.service.PostService;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest extends TestContext {

    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void testGetPosts_Success() throws Exception {
        PostView postView = PostView.builder()
                .id(1L)
                .title("title")
                .content("content")
                .tags(List.of("tag1", "tag2"))
                .likesCount(234L)
                .build();
        Page<PostView> postPage = new Page<>();
        postPage.setContent(List.of(postView));

        when(postService.findAll(Mockito.any(), Mockito.anyString())).thenReturn(postPage);

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("post-list"))
                .andExpect(model().attributeExists("page"));
    }

    @Test
    void testGetPosts_Error() throws Exception {
        when(postService.findAll(Mockito.any(), Mockito.anyString())).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("post-list"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testShowPostForm_AddPost() throws Exception {
        mockMvc.perform(get("/posts/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-post"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    void testCreatePost_Success() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("New Post");
        postDTO.setContent("Post content");

        Post savedPost = Post.builder()
                .id(1L)
                .title("New Title")
                .content("New Content")
                .tags(List.of(new Tag("Tag")))
                .build();

        when(postService.save(Mockito.any(PostDTO.class))).thenReturn(savedPost);

        mockMvc.perform(post("/posts/add")
                        .flashAttr("post", postDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    void testCreatePost_Error() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("New Post");
        postDTO.setContent("Post content");

        when(postService.save(Mockito.any(PostDTO.class))).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(post("/posts/add")
                        .flashAttr("post", postDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("add-post"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Error"));
    }

    @Test
    void testDeletePost_Success() throws Exception {
        long postId = 1L;
        mockMvc.perform(post("/posts/delete/{postId}", postId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void testDeletePost_Error() throws Exception {
        long postId = 1L;
        doThrow(new RuntimeException("Error")).when(postService).deleteById(postId);

        mockMvc.perform(post("/posts/delete/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Error"));
    }

}
