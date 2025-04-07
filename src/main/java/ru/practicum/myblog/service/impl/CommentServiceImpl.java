package ru.practicum.myblog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.myblog.dto.CommentDTO;
import ru.practicum.myblog.mapper.CommentMapper;
import ru.practicum.myblog.repository.CommentRepository;
import ru.practicum.myblog.service.CommentService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public void save(Long postId, Long commentId, CommentDTO dto) {
        commentRepository.save(commentMapper.fromDTO(postId, commentId, dto));
    }

    @Override
    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
