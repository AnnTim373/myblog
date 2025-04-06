package ru.practicum.myblog.mapper;

import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;

public interface PostMapper {

    Post fromDTO(PostDTO postDTO);

}
