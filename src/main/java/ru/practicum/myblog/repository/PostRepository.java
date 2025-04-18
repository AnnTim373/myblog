package ru.practicum.myblog.repository;

import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.page.Page;

import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(Long id);

    void editLikeCount(Long id, int i);

    void deleteById(Long id);

    Page<Post> findAll(Page<Post> page);

    Page<Post> findAllByTag(Page<Post> page, String tag);

    Optional<Post> findByTitle(String title);

}
