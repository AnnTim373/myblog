package ru.practicum.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.service.PostService;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/add")
    public String showPostForm(Model model) {
        model.addAttribute("post", new PostDTO());
        return "add-post";
    }

    @PostMapping("/add")
    public String createPost(@ModelAttribute(name = "post") PostDTO post) {
        Post savedPost = postService.save(post);
        return "redirect:/posts/" + savedPost.getId();
    }

}
