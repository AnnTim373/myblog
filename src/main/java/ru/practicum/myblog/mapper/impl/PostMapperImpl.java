package ru.practicum.myblog.mapper.impl;

import org.springframework.stereotype.Component;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.mapper.PostMapper;

@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post fromDTO(PostDTO postDTO) {
        if (postDTO == null) return null;
        return Post.builder()
                .id(postDTO.getId())
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .likesCount(postDTO.getLikesCount() == null ? 0 : postDTO.getLikesCount())
                .build();
    }

}
