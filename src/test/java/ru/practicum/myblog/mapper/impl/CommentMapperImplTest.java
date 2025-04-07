package ru.practicum.myblog.mapper.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.myblog.TestContext;
import ru.practicum.myblog.domain.Comment;
import ru.practicum.myblog.dto.CommentDTO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CommentMapperImplTest extends TestContext {

    private CommentMapperImpl commentMapper;

    @BeforeEach
    void setUp() {
        commentMapper = new CommentMapperImpl();
    }

    @Test
    void fromDTO_ShouldMapCommentDTOToComment() {
        Long postId = 1L;
        Long commentId = 2L;
        CommentDTO commentDTO = CommentDTO.builder()
                .content("This is a comment")
                .build();

        Comment comment = commentMapper.fromDTO(postId, commentId, commentDTO);

        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(commentId);
        assertThat(comment.getContent()).isEqualTo(commentDTO.getContent());
        assertThat(comment.getPostId()).isEqualTo(postId);
    }

    @Test
    void fromDTO_ShouldReturnNull_WhenDTOIsNull() {
        Comment comment = commentMapper.fromDTO(1L, 2L, null);

        assertThat(comment).isNull();
    }

    @Test
    void toDTOs_ShouldConvertListOfCommentsToListOfCommentDTOs() {
        Comment comment1 = Comment.builder()
                .id(1L)
                .content("First comment")
                .postId(1L)
                .build();
        Comment comment2 = Comment.builder()
                .id(2L)
                .content("Second comment")
                .postId(1L)
                .build();

        List<CommentDTO> commentDTOs = commentMapper.toDTOs(List.of(comment1, comment2));

        assertThat(commentDTOs).hasSize(2);
        assertThat(commentDTOs.get(0).getContent()).isEqualTo(comment1.getContent());
        assertThat(commentDTOs.get(1).getContent()).isEqualTo(comment2.getContent());
    }

    @Test
    void toDTO_ShouldMapCommentToCommentDTO()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Comment comment = Comment.builder()
                .id(1L)
                .content("This is a comment")
                .postId(1L)
                .build();

        Method method = CommentMapperImpl.class.getDeclaredMethod("toDTO", Comment.class);
        method.setAccessible(true);

        CommentDTO commentDTO = (CommentDTO) method.invoke(commentMapper, comment);

        assertThat(commentDTO).isNotNull();
        assertThat(commentDTO.getId()).isEqualTo(comment.getId());
        assertThat(commentDTO.getContent()).isEqualTo(comment.getContent());
    }

    @Test
    void toDTO_ShouldReturnNull_WhenCommentIsNull()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = CommentMapperImpl.class.getDeclaredMethod("toDTO", Comment.class);
        method.setAccessible(true);

        CommentDTO commentDTO = (CommentDTO) method.invoke(commentMapper, (Object) null);

        assertThat(commentDTO).isNull();
    }
}
