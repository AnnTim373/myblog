package ru.practicum.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.myblog.service.PostService;

@Controller
@RequestMapping("/posts/{postId}/like")
@RequiredArgsConstructor
public class LikeController {

    private final PostService postService;

    @PostMapping
    public String addReaction(@PathVariable(name = "postId") Long postId,
                              @RequestParam(name = "like") boolean like,
                              Model model) {
        try {
            postService.editLikesCount(postId, like);
            return "redirect:/posts/" + postId;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "post";
        }
    }

}
