package ru.practicum.myblog.repository;

import ru.practicum.myblog.domain.Post;

import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(Long id);

}
