package ru.practicum.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.myblog.dto.CommentDTO;
import ru.practicum.myblog.service.CommentService;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping({"/{postId}/comments", "/{postId}/comments/{commentId}"})
    public String saveComment(@PathVariable(name = "postId") Long postId,
                              @PathVariable(name = "commentId", required = false) Long commentId,
                              @ModelAttribute(name = "comment") CommentDTO commentDTO) {
        commentService.save(postId, commentId, commentDTO);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{postId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable(name = "postId") Long postId,
                                @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteById(commentId);
        return "redirect:/posts/" + postId;
    }

}
