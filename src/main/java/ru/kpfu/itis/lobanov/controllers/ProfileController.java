package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;
import ru.kpfu.itis.lobanov.util.exception.CloudinaryConfigException;

import java.util.List;

@Controller
@RequestMapping(ServerResources.PROFILE_URL)
@SessionAttributes(ServerResources.CURRENT_USER)
@RequiredArgsConstructor
public class ProfileController {
    private final PostService postService;

    @GetMapping
    public String getProfilePage(Model model, @ModelAttribute(ServerResources.CURRENT_USER) UserDto userDto) throws CloudinaryConfigException {
        throw new CloudinaryConfigException("AAAA");
//        List<PostDto> posts = postService.getAllFromUser(userDto.getLogin());
//        model.addAttribute(ServerResources.CURRENT_USER_POSTS, posts);
//        return ServerResources.PROFILE_PAGE;
    }
}
