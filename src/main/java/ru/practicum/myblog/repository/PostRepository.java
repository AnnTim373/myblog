package ru.practicum.myblog.repository;

import ru.practicum.myblog.domain.Post;

public interface PostRepository {

    Post save(Post post);

}
