package ru.practicum.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.myblog.dto.CommentDTO;
import ru.practicum.myblog.service.CommentService;

@Controller
@RequestMapping("/posts/{postId}")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping({"/comments", "/comments/{commentId}"})
    public String saveComment(@PathVariable(name = "postId") Long postId,
                              @PathVariable(name = "commentId", required = false) Long commentId,
                              @ModelAttribute(name = "comment") CommentDTO commentDTO,
                              Model model) {
        try {
            commentService.save(postId, commentId, commentDTO);
            return "redirect:/posts/" + postId;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "post";
        }
    }

    @PostMapping("/comments/{commentId}/delete")
    public String deleteComment(@PathVariable(name = "postId") Long postId,
                                @PathVariable(name = "commentId") Long commentId,
                                Model model) {
        try {
            commentService.deleteById(commentId);
            return "redirect:/posts/" + postId;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "post";
        }
    }

}
