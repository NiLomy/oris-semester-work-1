package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

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
            @ModelAttribute(ServerResources.CURRENT_USER) UserDto userDto
    ) {
        String response = postService.checkForValid(postName, postCategory, postText);
        if (response != null) return response;

        if (userDto != null) {
            if (!postService.isPostUnique(userDto.getLogin(), postName)) {
                return ServerResources.POST_ALREADY_EXISTS;
            }

            postService.save(postName, postCategory, postText, userDto.getLogin());
        }
        return ServerResources.SUCCESS_RESPONSE;
    }
}
