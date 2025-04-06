package ru.practicum.myblog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.dto.PostView;
import ru.practicum.myblog.error.PostException;
import ru.practicum.myblog.mapper.PostMapper;
import ru.practicum.myblog.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

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
        if (post.getId() != null) post.setComments(commentRepository.findByPostId(post.getId()));
        tagRepository.saveAll(post.getTags());
        return postRepository.save(post);
    }

    @Override
    public PostView getViewById(Long id) {
        return postRepository.findById(id).map(postMapper::toView)
                .orElseThrow(() -> new PostException("No such post found by id defined in url"));
    }

    @Override
    public PostDTO getDTOById(Long id) {
        return postRepository.findById(id).map(postMapper::toDTO)
                .orElseThrow(() -> new PostException("No such post found by id defined in url"));
    }

    @Override
    public void editLikesCount(Long postId, boolean like) {
        postRepository.editLikeCount(postId, like ? 1 : -1);
    }

}
