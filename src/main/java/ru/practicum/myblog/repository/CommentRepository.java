package ru.practicum.myblog.repository;

import ru.practicum.myblog.domain.Comment;

public interface CommentRepository {

    void save(Comment comment);

    void deleteById(Long commentId);

}
