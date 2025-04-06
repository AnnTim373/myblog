package ru.practicum.myblog.service;

import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.dto.PostView;

public interface PostService {

    Post save(PostDTO post);

    PostView getById(Long id);

    void editLikesCount(Long postId, boolean like);

}
