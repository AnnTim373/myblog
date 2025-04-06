package ru.practicum.myblog.mapper.impl;

import org.springframework.stereotype.Component;
import ru.practicum.myblog.domain.Image;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.mapper.PostMapper;

import java.io.IOException;
import java.util.Optional;

@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post fromDTO(PostDTO postDTO) throws IOException {
        if (postDTO == null) return null;
        Post post = Post.builder()
                .id(postDTO.getId())
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .likesCount(postDTO.getLikesCount() == null ? 0 : postDTO.getLikesCount())
                .build();
        post.setImage(getImageFromPostDTO(postDTO, post).orElse(null));
        return post;
    }

    private Optional<Image> getImageFromPostDTO(PostDTO postDTO, Post post) throws IOException {
        if (postDTO.getImage() == null || postDTO.getImage().isEmpty()) return Optional.empty();
        return Optional.of(Image.builder().post(post).data(postDTO.getImage().getBytes()).build());
    }

}
