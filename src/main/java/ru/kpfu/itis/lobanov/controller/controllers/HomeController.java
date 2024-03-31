package ru.kpfu.itis.lobanov.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.MessageService;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.model.service.impl.MessageServiceImpl;
import ru.kpfu.itis.lobanov.model.service.impl.PostServiceImpl;
import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
import ru.kpfu.itis.lobanov.security.CustomUserDetails;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@Controller
@RequestMapping("/")
@SessionAttributes(ServerResources.CURRENT_USER)
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final PostService postService;
    private final MessageService messageService;

    @GetMapping
    public String getHomePage(@CookieValue(value = "user", required = false) String user, HttpServletRequest req, Model model, Authentication authentication) {
        UserDto currentUser = userService.get(user);
        if (authentication != null) {
            CustomUserDetails u = (CustomUserDetails) authentication.getPrincipal();
            currentUser = userService.get(u.getUsername());
        }
        model.addAttribute(ServerResources.CURRENT_USER, currentUser);

        UserDto mostFrequentUser = messageService.getMostFrequentUser();
        PostDto mostPopularPost = postService.getMostPopularPost();

        model.addAttribute(ServerResources.MOST_ACTIVE_USER, mostFrequentUser);
        model.addAttribute(ServerResources.MOST_POPULAR_POST, mostPopularPost);
        return ServerResources.HOME_PAGE;
    }
}
