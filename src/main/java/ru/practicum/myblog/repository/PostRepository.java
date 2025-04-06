package ru.practicum.myblog.repository;

import ru.practicum.myblog.domain.Post;

import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(Long id);

    void editLikeCount(Long id, int i);

    void deleteById(Long id);

}
