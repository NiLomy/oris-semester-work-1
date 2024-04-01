package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.kpfu.itis.lobanov.model.service.MessageService;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

@Controller
@RequestMapping(ServerResources.HOME_URL)
@SessionAttributes(ServerResources.CURRENT_USER)
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final PostService postService;
    private final MessageService messageService;

    @GetMapping
    public String getHomePage(Model model) {
        UserDto currentUser = userService.getCurrentUser();
        model.addAttribute(ServerResources.CURRENT_USER, currentUser);

        UserDto mostFrequentUser = messageService.getMostFrequentUser();
        PostDto mostPopularPost = postService.getMostPopularPost();

        model.addAttribute(ServerResources.MOST_ACTIVE_USER, mostFrequentUser);
        model.addAttribute(ServerResources.MOST_POPULAR_POST, mostPopularPost);
        return ServerResources.HOME_PAGE;
    }
}
