package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import java.util.List;

@Controller
@RequestMapping(ServerResources.ANOTHER_PROFILE_URL)
@RequiredArgsConstructor
public class AnotherUserProfileController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping
    public String getAnotherUserProfilePage(@RequestParam(ServerResources.ANOTHER_USER) String nickname, Model model) {
        UserDto anotherUserDto = userService.get(nickname);
        model.addAttribute(ServerResources.ANOTHER_USER, anotherUserDto);
        UserDto currentUserDto = (UserDto) model.getAttribute(ServerResources.CURRENT_USER);

        List<PostDto> posts = postService.getAllFromUser(nickname);

        if (currentUserDto != null && anotherUserDto.getLogin().equals(currentUserDto.getLogin())) {
            model.addAttribute(ServerResources.CURRENT_USER_POSTS, posts);
            return ServerResources.PROFILE_PAGE;
        } else {
            model.addAttribute(ServerResources.ANOTHER_USER_POSTS, posts);
            return ServerResources.ANOTHER_USER_PROFILE_PAGE;
        }
    }
}
