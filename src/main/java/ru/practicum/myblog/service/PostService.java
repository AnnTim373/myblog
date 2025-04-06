package ru.practicum.myblog.service;

import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.dto.PostView;
import ru.practicum.myblog.dto.page.Page;

public interface PostService {

    Page<PostView> findAll(Page<Post> page);

    Post save(PostDTO post);

    PostView getViewById(Long id);

    PostDTO getDTOById(Long id);

    void editLikesCount(Long id, boolean like);

    void deleteById(Long id);

}
