package ru.practicum.myblog.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.myblog.TestContext;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.dto.PostView;
import ru.practicum.myblog.error.PostException;
import ru.practicum.myblog.mapper.PostMapper;
import ru.practicum.myblog.repository.PostRepository;
import ru.practicum.myblog.repository.TagRepository;
import ru.practicum.myblog.validation.PostValidator;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest extends TestContext {

    @Mock
    private PostRepository postRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private PostValidator postValidator;

    @InjectMocks
    private PostServiceImpl postService;

    private PostDTO postDTO;
    private Post post;
    private PostView postView;

    @BeforeEach
    void setUp() {
        postDTO = new PostDTO();
        post = new Post();
        postView = new PostView();
    }

    @Test
    void save_ShouldSavePost_WhenValidPostDTO() throws Exception {
        when(postMapper.fromDTO(postDTO)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);

        doNothing().when(postValidator).validate(postDTO);

        Post savedPost = postService.save(postDTO);

        assertThat(savedPost).isNotNull();
        verify(postRepository).save(post);
        verify(tagRepository).saveAll(post.getTags());
    }

    @Test
    void save_ShouldThrowException_WhenIOExceptionOccurs() throws Exception {
        when(postMapper.fromDTO(postDTO)).thenThrow(new IOException("Error"));

        assertThatThrownBy(() -> postService.save(postDTO))
                .isInstanceOf(PostException.class)
                .hasMessage("Error");
    }

    @Test
    void getViewById_ShouldReturnPostView_WhenPostExists() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(post));
        when(postMapper.toView(post)).thenReturn(postView);

        PostView result = postService.getViewById(postId);

        assertThat(result).isEqualTo(postView);
    }

    @Test
    void getViewById_ShouldThrowException_WhenPostNotFound() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> postService.getViewById(postId))
                .isInstanceOf(PostException.class)
                .hasMessage("No such post found by id defined in url");
    }

    @Test
    void editLikesCount_ShouldEditLikesCount_WhenValid() {
        Long postId = 1L;
        boolean like = true;
        doNothing().when(postRepository).editLikeCount(postId, 1);

        postService.editLikesCount(postId, like);

        verify(postRepository).editLikeCount(postId, 1);
    }

    @Test
    void deleteById_ShouldDeletePost_WhenPostExists() {
        Long postId = 1L;
        doNothing().when(postRepository).deleteById(postId);

        postService.deleteById(postId);

        verify(postRepository).deleteById(postId);
    }

}
