package ru.practicum.myblog.mapper;

import ru.practicum.myblog.domain.Comment;
import ru.practicum.myblog.dto.CommentDTO;

import java.util.List;

public interface CommentMapper {

    Comment fromDTO(Long postId, Long commentId, CommentDTO dto);

    List<CommentDTO> toDTOs(List<Comment> comments);
}
