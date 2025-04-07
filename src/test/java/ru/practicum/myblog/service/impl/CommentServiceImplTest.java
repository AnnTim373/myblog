package ru.practicum.myblog.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.myblog.TestContext;
import ru.practicum.myblog.domain.Comment;
import ru.practicum.myblog.dto.CommentDTO;
import ru.practicum.myblog.mapper.CommentMapper;
import ru.practicum.myblog.repository.CommentRepository;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest extends TestContext {

    @Mock
    private CommentRepository commentRepository;
    
    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private CommentDTO commentDTO;

    @BeforeEach
    void setUp() {
        commentDTO = new CommentDTO();
    }

    @Test
    void save_ShouldSaveComment_WhenValidCommentDTO() {
        Long postId = 1L;
        Long commentId = 1L;

        when(commentMapper.fromDTO(postId, commentId, commentDTO)).thenReturn(new Comment());
        doNothing().when(commentRepository).save(any());

        commentService.save(postId, commentId, commentDTO);

        verify(commentRepository).save(any());
    }

    @Test
    void deleteById_ShouldDeleteComment_WhenCommentExists() {
        Long commentId = 1L;
        doNothing().when(commentRepository).deleteById(commentId);

        commentService.deleteById(commentId);

        verify(commentRepository).deleteById(commentId);
    }

    @Test
    void deleteById_ShouldThrowException_WhenCommentDoesNotExist() {
        Long commentId = 1L;
        doThrow(new RuntimeException("Comment not found")).when(commentRepository).deleteById(commentId);

        assertThatThrownBy(() -> commentService.deleteById(commentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Comment not found");
    }

}
