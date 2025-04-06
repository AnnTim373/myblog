package ru.practicum.myblog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.mapper.PostMapper;
import ru.practicum.myblog.repository.PostRepository;
import ru.practicum.myblog.service.PostService;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public Post save(PostDTO postDTO) {
        Post post = postMapper.fromDTO(postDTO);
        return postRepository.save(post);
    }

}
