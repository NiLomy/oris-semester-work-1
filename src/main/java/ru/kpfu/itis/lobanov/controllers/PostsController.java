package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.PostDto;

import java.util.List;

@Controller
@RequestMapping(ServerResources.POSTS_URL)
@RequiredArgsConstructor
public class PostsController {
    private final PostService postService;

    @GetMapping
    public String getPostsPage(Model model) {
        List<PostDto> posts = postService.getAll();
        model.addAttribute(ServerResources.POSTS, posts);
        return ServerResources.POSTS_PAGE;
    }
}
