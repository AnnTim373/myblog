package ru.practicum.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.dto.PostView;
import ru.practicum.myblog.service.PostService;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping({"/add", "/edit/{postId}"})
    public String showPostForm(@PathVariable(name = "postId", required = false) Long postId, Model model) {
        PostDTO post = postId == null ? new PostDTO() : postService.getDTOById(postId);
        model.addAttribute("post", post);
        return "add-post";
    }

    @PostMapping("/add")
    public String createPost(@ModelAttribute(name = "post") PostDTO post) {
        Post savedPost = postService.save(post);
        return "redirect:/posts/" + savedPost.getId();
    }

    @GetMapping("/{id}")
    public String showPostDetails(@PathVariable(name = "id") Long id, Model model) {
        PostView post = postService.getViewById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable(name = "postId") Long postId) {
        postService.deleteById(postId);
        return "redirect:/posts";
    }

}
