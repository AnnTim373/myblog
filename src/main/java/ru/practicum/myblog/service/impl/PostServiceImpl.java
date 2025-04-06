package ru.practicum.myblog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.practicum.myblog.domain.Image;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.error.PostException;
import ru.practicum.myblog.mapper.PostMapper;
import ru.practicum.myblog.repository.PostRepository;
import ru.practicum.myblog.service.PostService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public Post save(PostDTO postDTO) {
        Post post;
        try {
            post = postMapper.fromDTO(postDTO);
        } catch (IOException e) {
            throw new PostException(e.getMessage());
        }

        return postRepository.save(post);
    }

}
