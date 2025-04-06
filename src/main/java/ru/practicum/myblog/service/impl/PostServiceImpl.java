package ru.practicum.myblog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.error.PostException;
import ru.practicum.myblog.mapper.PostMapper;
import ru.practicum.myblog.repository.PostRepository;
import ru.practicum.myblog.repository.TagRepository;
import ru.practicum.myblog.service.PostService;
import ru.practicum.myblog.validation.PostValidator;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    private final PostMapper postMapper;
    private final PostValidator postValidator;

    @Override
    public Post save(PostDTO postDTO) {
        Post post;
        postValidator.validate(postDTO);
        try {
            post = postMapper.fromDTO(postDTO);
        } catch (IOException e) {
            throw new PostException(e.getMessage());
        }
        tagRepository.saveAll(post.getTags());
        return postRepository.save(post);
    }

}
