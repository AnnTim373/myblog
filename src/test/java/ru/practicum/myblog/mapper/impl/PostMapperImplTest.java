package ru.practicum.myblog.mapper.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.myblog.TestContext;
import ru.practicum.myblog.domain.Image;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.domain.Tag;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.dto.PostView;
import ru.practicum.myblog.dto.page.Page;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PostMapperImplTest extends TestContext {

    @InjectMocks
    private PostMapperImpl postMapper;

    private PostDTO postDTO;
    private Post post;

    @BeforeEach
    void setUp() {
        postDTO = PostDTO.builder()
                .id(1L)
                .title("Post Title")
                .content("Post Content")
                .likesCount(10L)
                .tags("#tag1 #tag2")
                .build();

        post = Post.builder()
                .id(1L)
                .title("Post Title")
                .content("Post Content")
                .likesCount(10L)
                .tags(Arrays.asList(new Tag("tag1"), new Tag("tag2")))
                .build();
    }

    @Test
    void fromDTO_shouldMapPostDTOToPost() throws Exception {
        Post result = postMapper.fromDTO(postDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(postDTO.getId());
        assertThat(result.getTitle()).isEqualTo(postDTO.getTitle());
        assertThat(result.getContent()).isEqualTo(postDTO.getContent());
        assertThat(result.getLikesCount()).isEqualTo(postDTO.getLikesCount());
        assertThat(result.getTags()).hasSize(2);
        assertThat(result.getTags().get(0).getValue()).isEqualTo("tag1");
        assertThat(result.getTags().get(1).getValue()).isEqualTo("tag2");
    }

    @Test
    void toView_shouldMapPostToPostView() {
        PostView result = postMapper.toView(post);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(post.getId());
        assertThat(result.getTitle()).isEqualTo(post.getTitle());
        assertThat(result.getContent()).isEqualTo(post.getContent());
        assertThat(result.getLikesCount()).isEqualTo(post.getLikesCount());
        assertThat(result.getTags()).containsExactly("tag1", "tag2");
    }

    @Test
    void toViews_shouldMapPageOfPostsToPageOfPostViews() {
        Page<Post> page = new Page<>(List.of(post), 1L, 1, 1);

        Page<PostView> result = postMapper.toViews(page);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getId()).isEqualTo(post.getId());
        assertThat(result.getContent().getFirst().getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    void toDTO_shouldMapPostToPostDTO() {

        PostDTO result = postMapper.toDTO(post);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(post.getId());
        assertThat(result.getTitle()).isEqualTo(post.getTitle());
        assertThat(result.getContent()).isEqualTo(post.getContent());
        assertThat(result.getLikesCount()).isEqualTo(post.getLikesCount());
        assertThat(result.getTags()).isEqualTo("#tag1 #tag2");
        assertThat(result.getOldImage()).isNull();
    }

    @Test
    @SuppressWarnings("unchecked")
    void getTagsFromDTO_shouldExtractTagsFromDTO()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method method = PostMapperImpl.class.getDeclaredMethod("getTagsFromDTO", PostDTO.class);
        method.setAccessible(true);

        List<Tag> result = (List<Tag>) method.invoke(postMapper, postDTO);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getValue()).isEqualTo("tag1");
        assertThat(result.get(1).getValue()).isEqualTo("tag2");
    }

    @Test
    @SuppressWarnings("unchecked")
    void getImageFromPostDTO_shouldReturnOptionalImage()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String base64Image = Base64.getEncoder().encodeToString("image data".getBytes());
        postDTO.setOldImage(base64Image);

        Method method = PostMapperImpl.class.getDeclaredMethod("getImageFromPostDTO", PostDTO.class, Post.class);
        method.setAccessible(true);

        Optional<Image> result = (Optional<Image>) method.invoke(postMapper, postDTO, post);

        assertThat(result).isPresent();
        assertThat(result.get().getData()).isEqualTo(Base64.getDecoder().decode(base64Image));
    }

}
