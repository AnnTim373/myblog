package ru.practicum.myblog.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.myblog.domain.Image;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.domain.Tag;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.dto.PostView;
import ru.practicum.myblog.dto.page.Page;
import ru.practicum.myblog.mapper.CommentMapper;
import ru.practicum.myblog.mapper.PostMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapperImpl implements PostMapper {

    private final CommentMapper commentMapper;

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
        post.setTags(getTagsFromDTO(postDTO));
        return post;
    }

    @Override
    public PostView toView(Post post) {
        return PostView.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likesCount(post.getLikesCount())
                .tags(post.getTags().stream().map(Tag::getValue).collect(Collectors.toList()))
                .image(post.getImage() != null ? Base64.getEncoder().encodeToString(post.getImage().getData()) : null)
                .comments(commentMapper.toDTOs(post.getComments()))
                .build();
    }

    @Override
    public Page<PostView> toViews(Page<Post> posts) {
        List<PostView> contentView = posts.getContent().stream().map(this::toView).toList();
        return new Page<>(contentView, posts.getTotalCount(), posts.getPageSize(), posts.getPageNumber());
    }

    @Override
    public PostDTO toDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likesCount(post.getLikesCount())
                .tags(getTagsForDTO(post))
                .oldImage(post.getImage() != null ?
                        Base64.getEncoder().encodeToString(post.getImage().getData()) : null)
                .build();
    }

    private Optional<Image> getImageFromPostDTO(PostDTO postDTO, Post post) throws IOException {
        if (postDTO.getImage() == null || postDTO.getImage().isEmpty()) {
            if (postDTO.getOldImage() != null) {
                return Optional.of(Image.builder()
                        .post(post)
                        .data(Base64.getDecoder().decode(postDTO.getOldImage()))
                        .build());
            } else return Optional.empty();
        }
        return Optional.of(Image.builder()
                .post(post)
                .data(postDTO.getImage().getBytes())
                .build());
    }

    private List<Tag> getTagsFromDTO(PostDTO postDTO) {
        return Arrays.stream(postDTO.getTags().split("#"))
                .filter(tag -> !tag.trim().isEmpty())
                .map(tag -> new Tag(tag.trim().toLowerCase()))
                .toList();
    }

    private String getTagsForDTO(Post post) {
        if (post.getTags() == null || post.getTags().isEmpty()) return "";
        return "#" + post.getTags().stream().map(Tag::getValue).collect(Collectors.joining(" #"));
    }

}
