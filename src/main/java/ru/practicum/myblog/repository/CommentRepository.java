package ru.practicum.myblog.repository;

import ru.practicum.myblog.domain.Comment;

import java.util.List;

public interface CommentRepository {

    Comment save(Comment comment);

    void deleteById(Long commentId);

    List<Comment> findByPostId(Long postId);
}
