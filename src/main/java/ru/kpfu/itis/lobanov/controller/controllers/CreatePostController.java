package ru.kpfu.itis.lobanov.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(ServerResources.CREATE_POST_URL)
@SessionAttributes(ServerResources.CURRENT_USER)
@RequiredArgsConstructor
public class CreatePostController {
    private final PostService postService;

    @GetMapping
    public String getCreatePostPage() {
        return ServerResources.CREATE_POST_PAGE;
    }

    @PostMapping
    @ResponseBody
    public String createPost(
            @RequestParam(ServerResources.POST_NAME) String postName,
            @RequestParam(ServerResources.POST_CATEGORY) String postCategory,
            @RequestParam(ServerResources.POST_TEXT) String postText,
            Model model,
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto userDto,
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        if (!postService.isPostValid(postName, postCategory, postText, req, resp)) return ServerResources.CREATE_POST_PAGE;

        if (userDto != null) {
            if (!postService.isPostUnique(userDto.getLogin(), postName)) {
                resp.getWriter().write(ServerResources.POST_ALREADY_EXISTS);
                return ServerResources.CREATE_POST_PAGE;
            }

            postService.save(postName, postCategory, postText, userDto.getLogin());
            return "redirect:" + String.format(ServerResources.POST_OF_AUTHOR_URL, postName, userDto.getLogin());
        }
        return "";
    }
}
