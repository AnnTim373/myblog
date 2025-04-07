package ru.practicum.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.practicum.myblog.domain.Post;
import ru.practicum.myblog.dto.PostDTO;
import ru.practicum.myblog.dto.PostView;
import ru.practicum.myblog.dto.page.Page;
import ru.practicum.myblog.service.PostService;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public String getPosts(Model model,
                           @ModelAttribute Page<Post> page,
                           @RequestParam(name = "search", required = false) String search) {
        try {
            Page<PostView> postPage = postService.findAll(page, search);
            model.addAttribute("search", search)
                    .addAttribute("page", postPage);
            return "post-list";
        } catch (Exception e) {
            model.addAttribute("search", null)
                    .addAttribute("page", new Page<PostView>())
                    .addAttribute("error", e.getMessage());
            return "post-list";
        }
    }


    @GetMapping({"/add", "/edit/{postId}"})
    public String showPostForm(@PathVariable(name = "postId", required = false) Long postId, Model model) {
        try {
            PostDTO post = postId == null ? new PostDTO() : postService.getDTOById(postId);
            model.addAttribute("post", post);
            return "add-post";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage())
                    .addAttribute("post", new PostDTO());
            return "add-post";
        }
    }

    @PostMapping("/add")
    public String createPost(@ModelAttribute(name = "post") PostDTO post, Model model) {
        try {
            Post savedPost = postService.save(post);
            return "redirect:/posts/" + savedPost.getId();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage())
                    .addAttribute("post", post);
            return "add-post";
        }
    }

    @GetMapping("/{id}")
    public String showPostDetails(@PathVariable(name = "id") Long id, Model model) {
        try {
            PostView post = postService.getViewById(id);
            model.addAttribute("post", post);
            return "post";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage())
                    .addAttribute("post", new PostView());
            return "post";
        }
    }

    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable(name = "postId") Long postId, Model model) {
        try {
            postService.deleteById(postId);
            return "redirect:/posts";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "post";
        }
    }

}
