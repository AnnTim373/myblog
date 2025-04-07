package ru.practicum.myblog.service;

import ru.practicum.myblog.dto.CommentDTO;

public interface CommentService {

    void save(Long postId, Long commentId, CommentDTO dto);

    void deleteById(Long commentId);
}
