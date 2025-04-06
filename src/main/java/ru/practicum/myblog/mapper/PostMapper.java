package ru.practicum.myblog.mapper;

import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;

import java.io.IOException;

public interface PostMapper {

    Post fromDTO(PostDTO postDTO) throws IOException;

}
