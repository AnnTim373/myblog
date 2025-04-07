package ru.practicum.myblog.mapper.impl;

import org.springframework.stereotype.Component;
import ru.practicum.myblog.domain.Comment;
import ru.practicum.myblog.dto.CommentDTO;
import ru.practicum.myblog.mapper.CommentMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment fromDTO(Long postId, Long commentId, CommentDTO dto) {
        if (dto == null) return null;
        return Comment.builder()
                .id(commentId)
                .content(dto.getContent())
                .postId(postId)
                .build();
    }

    @Override
    public List<CommentDTO> toDTOs(List<Comment> comments) {
        return comments.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private CommentDTO toDTO(Comment comment) {
        if (comment == null) return null;
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }

}
