package ru.practicum.myblog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockReset;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.myblog.TestContext;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.repository.PostRepository;
import ru.practicum.myblog.service.PostService;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Import({TestContext.class})
@AutoConfigureMockMvc
public class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    private final PostDTO postDTO = PostDTO.builder()
            .content("Test Content")
            .title("Test Title")
            .tags("#testTag1 #testTag2")
            .build();

    @MockitoSpyBean(reset = MockReset.AFTER)
    private PostRepository postRepository;

    @Test
    public void testCreatePostWithSuccess() throws Exception {
        mockMvc.perform(post("/posts/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "New Post Title")
                        .param("content", postDTO.getContent())
                        .param("tags", postDTO.getTags()))
                .andExpect(status().is3xxRedirection());

        assertTrue(postRepository.findByTitle("New Post Title").isPresent());
    }

    @Test
    public void testGetPostsWithSuccess() throws Exception {
        postService.save(postDTO);
        mockMvc.perform(get("/posts")
                        .param("pageSize", "5")
                        .param("pageNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("post-list"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attribute("page",
                                hasProperty("content",
                                        hasItem(hasProperty("title", notNullValue()))
                                )
                        )
                );
    }

    @Test
    public void testShowPostFormAddWithSuccess() throws Exception {
        mockMvc.perform(get("/posts/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-post"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    public void testShowPostFormEditWithSuccess() throws Exception {
        Post saved = postService.save(postDTO);
        mockMvc.perform(get("/posts/edit/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("add-post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attribute("post", hasProperty("title", is(saved.getTitle()))))
                .andExpect(model().attribute("post", hasProperty("content", is(saved.getContent()))))
                .andExpect(model().attribute("post", hasProperty("image", is(saved.getImage()))))
                .andExpect(model().attribute("post", hasProperty("id", is(saved.getId()))));
    }


    @Test
    public void testShowPostDetailsWithSuccess() throws Exception {
        Post saved = postService.save(postDTO);
        mockMvc.perform(get("/posts/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attribute("post", hasProperty("title", is(saved.getTitle()))))
                .andExpect(model().attribute("post", hasProperty("content", is(saved.getContent()))))
                .andExpect(model().attribute("post", hasProperty("image", is(saved.getImage()))))
                .andExpect(model().attribute("post", hasProperty("id", is(saved.getId()))));
    }

    @Test
    public void testDeletePostWithSuccess() throws Exception {
        Post saved = postService.save(postDTO);

        mockMvc.perform(post("/posts/delete/" + saved.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));

        assertFalse(postRepository.findById(postDTO.getId()).isPresent());
    }

    @Test
    public void testGetPostsWithError() throws Exception {
        doThrow(new RuntimeException("Error while fetching posts")).when(postRepository).findAll(any());

        mockMvc.perform(get("/posts")
                        .param("pageSize", "5")
                        .param("pageNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("post-list"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attribute("error", notNullValue()));
    }

}
