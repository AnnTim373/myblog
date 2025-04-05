package ru.practicum.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostsController {

    @GetMapping("/add")
    public String getPostsAddForm() {
        return "add-post";
    }

}
