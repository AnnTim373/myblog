package ru.practicum.myblog.service;

import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;

public interface PostService {

    Post save(PostDTO post);

}
